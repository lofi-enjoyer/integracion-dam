package me.lofienjoyer.quiet.postservice.service;

import me.lofienjoyer.quiet.basemodel.dto.LikePostDto;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface LikeService {

    /**
     * @param dto DTO with the necessary data
     * @param authentication User's authentication
     * @return Like count of the post
     */
    Mono<Integer> likePost(LikePostDto dto, Authentication authentication);

    /**
     * @param dto DTO with the necessary data
     * @param authentication User's authentication
     * @return Like count of the post
     */
    Mono<Integer> unlikePost(LikePostDto dto, Authentication authentication);

}
