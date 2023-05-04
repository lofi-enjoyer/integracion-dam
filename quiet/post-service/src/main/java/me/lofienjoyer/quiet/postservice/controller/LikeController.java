package me.lofienjoyer.quiet.postservice.controller;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dto.LikePostDto;
import me.lofienjoyer.quiet.postservice.service.LikeService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    public Mono<Integer> likePost(@RequestBody LikePostDto dto, Authentication authentication) {
        return likeService.likePost(dto, authentication);
    }

    @PostMapping("/unlike")
    public Mono<Integer> unlikePost(@RequestBody LikePostDto dto, Authentication authentication) {
        return likeService.unlikePost(dto, authentication);
    }

}
