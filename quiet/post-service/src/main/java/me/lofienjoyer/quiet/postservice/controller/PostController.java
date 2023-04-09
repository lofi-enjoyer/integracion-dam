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

/**
 * Manages requests to the {@code /api/posts} endpoints
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * @param dto DTO containing the data for the new post
     * @param authentication Authentication data of the user sending the request
     * @return DTO containing the data of the post created
     */
    @PostMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public Mono<PostDto> createPost(@RequestBody CreatePostDto dto, Authentication authentication) {
        return postService.createPost(dto, authentication);
    }

    /**
     * @param authentication Authentication data of the user sending the request
     * @return List of DTOs containing the data of the posts in the user's feed, ordered by date
     */
    @GetMapping("/feed")
    @PreAuthorize("isAuthenticated()")
    public Flux<PostDto> getCurrentUserFeed(Authentication authentication) {
        return postService.getCurrentUserFeed(authentication);
    }

    /**
     * @param authentication Authentication data of the user sending the request
     * @return List of posts of the user's profile, ordered by date
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Flux<PostDto> getOwnPosts(Authentication authentication) {
        return postService.getCurrentUserPosts(authentication);
    }

    //TODO Use requests params instead of path variables
    /**
     * @param username Username of the profile to get the posts of
     * @return List of posts the specified profile, ordered by date
     */
    @GetMapping("/get/{username}")
    public Flux<PostDto> getPostsForProfile(@PathVariable("username") String username) {
        return postService.getUserPosts(username);
    }

}
