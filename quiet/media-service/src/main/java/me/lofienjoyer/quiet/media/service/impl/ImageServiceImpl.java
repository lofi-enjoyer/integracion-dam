package me.lofienjoyer.quiet.media.service.impl;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dto.ProfileDto;
import me.lofienjoyer.quiet.media.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Value("${quiet.profile-img-dir}")
    private String profileImgDir;

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<String> uploadProfileImage(Authentication authentication, Mono<FilePart> multipartFile) {
        Path folder = Paths.get(profileImgDir);

        Mono<ProfileDto> profileDtoMono = webClientBuilder.build().get().uri("http://user-service/api/profiles/me")
                .cookie("token", authentication.getCredentials().toString())
                .retrieve()
                .bodyToMono(ProfileDto.class);

        return Mono.zip(profileDtoMono, multipartFile).flatMap(objects -> {
                    String filename = objects.getT1().getId() + getFileExtension(objects.getT2().filename());
                    return objects.getT2().transferTo(folder.resolve(filename))
                            .then(Mono.just(filename));
                });
    }

    private static String getFileExtension(String name) {
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }

}
