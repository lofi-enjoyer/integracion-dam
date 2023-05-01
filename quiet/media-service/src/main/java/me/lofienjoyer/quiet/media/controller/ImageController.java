package me.lofienjoyer.quiet.media.controller;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.media.service.ImageService;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/uploadprofile")
    public Mono<String> uploadProfileImage(@RequestPart("file") Mono<FilePart> file, Authentication authentication) {
        return imageService.uploadProfileImage(authentication, file);
    }

}
