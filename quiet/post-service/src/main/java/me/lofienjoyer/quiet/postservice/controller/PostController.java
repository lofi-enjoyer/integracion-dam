package me.lofienjoyer.quiet.postservice.controller;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dto.CreatePostDto;
import me.lofienjoyer.quiet.basemodel.dto.PostDto;
import me.lofienjoyer.quiet.postservice.service.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public Mono<PostDto> createPost(@RequestBody CreatePostDto dto, Authentication authentication) {
        return postService.createPost(dto, authentication);
    }

    @GetMapping("/feed")
    @PreAuthorize("isAuthenticated()")
    public Flux<PostDto> getCurrentUserFeed(Authentication authentication) {
        return postService.getCurrentUserFeed(authentication);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Flux<PostDto> getOwnPosts(Authentication authentication) {
        return postService.getCurrentUserPosts(authentication);
    }

    //TODO Use requests params instead of path variables
    @GetMapping("/get/{username}")
    public Flux<PostDto> getPostsForProfile(@PathVariable("username") String username) {
        return postService.getUserPosts(username);
    }

}
