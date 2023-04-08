package me.lofienjoyer.quiet.postservice.service;

import me.lofienjoyer.quiet.basemodel.dto.CreatePostDto;
import me.lofienjoyer.quiet.basemodel.dto.PostDto;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

    Mono<PostDto> createPost(CreatePostDto dto, Authentication authentication);

    Flux<PostDto> getCurrentUserFeed(Authentication authentication);

    Flux<PostDto> getUserFeed(String username);

    Flux<PostDto> getCurrentUserPosts(Authentication authentication);

    Flux<PostDto> getUserPosts(String username);

}
