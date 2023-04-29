package me.lofienjoyer.quiet.postservice.service;

import me.lofienjoyer.quiet.basemodel.dto.LikePostDto;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface LikeService {

    Mono<Integer> likePost(LikePostDto dto, Authentication authentication);
    Mono<Integer> unlikePost(LikePostDto dto, Authentication authentication);

}
