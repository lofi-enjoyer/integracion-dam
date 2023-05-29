package me.lofienjoyer.quiet.postservice.controller;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dto.*;
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
    @PostMapping("/feed")
    @PreAuthorize("isAuthenticated()")
    public Flux<PostDto> getCurrentUserFeed(@RequestBody FeedRequest feedRequest, Authentication authentication) {
        return postService.getCurrentUserFeed(authentication, feedRequest.getPage());
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

    @GetMapping("/alltags")
    public Flux<PostTagDto> getAllPostTags() {
        return postService.getAllPostTags();
    }

    @GetMapping("/mytags")
    @PreAuthorize("isAuthenticated()")
    public Flux<PostTagDto> getBlockedPostTags(Authentication authentication) {
        return postService.getBlockedPostTags(authentication);
    }

    @PostMapping("/savetags")
    @PreAuthorize("isAuthenticated()")
    public Flux<PostTagDto> saveBlockedPostTags(@RequestBody SaveBlockedTagsDto dto, Authentication authentication) {
        return postService.saveBlockedPostTags(dto, authentication);
    }

    @PostMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public Flux<PostDto> searchPosts(@RequestBody SearchRequestDto searchRequestDto, Authentication authentication) {
        return postService.searchPosts(authentication, searchRequestDto);
    }

}
