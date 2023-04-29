package me.lofienjoyer.quiet.postservice.service.impl;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dao.PostDao;
import me.lofienjoyer.quiet.basemodel.dto.LikePostDto;
import me.lofienjoyer.quiet.basemodel.entity.Post;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.postservice.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostDao postDao;

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<Integer> likePost(LikePostDto dto, Authentication authentication) {
        Post post = postDao.findById(dto.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post id not found"));

        return getCurrentProfile(authentication)
                .map(profile -> {
                    post.getLikes().add(profile);
                    return postDao.save(post).getLikes().size();
                });
    }

    @Override
    public Mono<Integer> unlikePost(LikePostDto dto, Authentication authentication) {
        Post post = postDao.findById(dto.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post id not found"));

        return getCurrentProfile(authentication)
                .map(profile -> {
                    post.getLikes().remove(profile);
                    return postDao.save(post).getLikes().size();
                });
    }

    private Mono<Profile> getCurrentProfile(Authentication authentication) {
        return webClientBuilder.build().get().uri("http://user-service/api/profiles/me")
                .cookie("token", authentication.getCredentials().toString())
                .retrieve()
                .bodyToMono(Profile.class);
    }

}
