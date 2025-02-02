package me.lofienjoyer.quiet.postservice.service;

import me.lofienjoyer.quiet.basemodel.dto.*;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Handles posts
 */
public interface PostService {

    /**
     * @param dto DTO containing the data for the new post
     * @param authentication Authentication data of the user sending the request
     * @return DTO containing the data of the post created
     */
    Mono<PostDto> createPost(CreatePostDto dto, Authentication authentication);

    /**
     * @param authentication Authentication data of the user sending the request
     * @param page Page of the feed
     * @return List of DTOs containing the data of the posts in the user's feed, ordered by date
     */
    Flux<PostDto> getCurrentUserFeed(Authentication authentication, int page);

    /**
     * @param username Username of the profile to get the profile of
     * @param page Page of the feed
     * @return List of posts of the specified profile, ordered by date
     */
    Flux<PostDto> getUserFeed(String username, int page);

    /**
     * @param authentication Authentication data of the user sending the request
     * @return List of posts of the user's profile, ordered by date
     */
    Flux<PostDto> getCurrentUserPosts(Authentication authentication);

    /**
     * @param username Username of the profile to get the posts of
     * @return List of posts the specified profile, ordered by date
     */
    Flux<PostDto> getUserPosts(String username);

    /**
     * @return All post tags
     */
    Flux<PostTagDto> getAllPostTags();

    /**
     * @param authentication User's authentication
     * @return List of the blocked tags for the current user
     */
    Flux<PostTagDto> getBlockedPostTags(Authentication authentication);

    /**
     * @param dto DTO with the necessary data
     * @param authentication User's authentication
     * @return Saves blocked tags for the current user
     */
    Flux<PostTagDto> saveBlockedPostTags(SaveBlockedTagsDto dto, Authentication authentication);

    /**
     * @param authentication User's authentication
     * @param dto DTO with the necessary data
     * @return List of posts found
     */
    Flux<PostDto> searchPosts(Authentication authentication, SearchRequestDto dto);

}
