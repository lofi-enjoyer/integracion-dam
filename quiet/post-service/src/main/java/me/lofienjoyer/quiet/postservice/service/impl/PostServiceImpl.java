package me.lofienjoyer.quiet.postservice.service.impl;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dao.PostDao;
import me.lofienjoyer.quiet.basemodel.dao.PostTagDao;
import me.lofienjoyer.quiet.basemodel.dao.ProfileDao;
import me.lofienjoyer.quiet.basemodel.dao.UserInfoDao;
import me.lofienjoyer.quiet.basemodel.dto.*;
import me.lofienjoyer.quiet.basemodel.entity.Post;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import me.lofienjoyer.quiet.postservice.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link PostService}
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostDao postDao;
    private final PostTagDao postTagDao;
    private final ProfileDao profileDao;
    private final UserInfoDao userInfoDao;

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<PostDto> createPost(CreatePostDto dto, Authentication authentication) {
        if (dto.getContent().trim().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content cannot be empty.");

        return webClientBuilder.build().get().uri("http://user-service/api/profiles/me")
                .cookie("token", authentication.getCredentials().toString())
                .retrieve()
                .bodyToMono(Profile.class)
                .map(profile -> {
                    Post post = new Post();
                    post.setContent(dto.getContent().trim());
                    post.setDate(new Date());
                    post.setProfile(profile);
                    post.setLikes(Set.of());
                    post.setTags(Set.copyOf(postTagDao.findAllById(dto.getTagIds())));
                    post = postDao.save(post);

                    return new PostDto(post);
                });
    }

    // TODO: Support to show new posts published when scrolling and it not affecting the pagination
    @Override
    public Flux<PostDto> getCurrentUserFeed(Authentication authentication, int page) {
        Pageable pageable = PageRequest.of(page, 10);

        return webClientBuilder.build().get().uri("http://user-service/api/profiles/me")
                .cookie("token", authentication.getCredentials().toString())
                .retrieve()
                .bodyToMono(ProfileDto.class)
                .map(profile -> {
                    return postDao.findByIdIn(postDao.getPostsIdsFromFollowed(profile.getId(), pageable))
                            .stream().map(post -> new PostDto(post, profile))
                            .collect(Collectors.toList());
                })
                .flatMapMany(Flux::fromIterable);
    }

    // TODO: Support to show new posts published when scrolling and it not affecting the pagination
    @Override
    public Flux<PostDto> getUserFeed(String username, int page) {
        Pageable pageable = PageRequest.of(page, 10);

        return webClientBuilder.build().get().uri("http://user-service/api/profiles/" + username)
                .retrieve()
                .bodyToMono(ProfileDto.class)
                .map(profile -> {
                    return postDao.findByIdIn(postDao.getPostsIdsFromFollowed(profile.getId(), pageable))
                            .stream().map(post -> new PostDto(post, profile))
                            .collect(Collectors.toList());
                })
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Flux<PostDto> getCurrentUserPosts(Authentication authentication) {
        return webClientBuilder.build().get().uri("http://user-service/api/profiles/me")
                .cookie("token", authentication.getCredentials().toString())
                .retrieve()
                .bodyToMono(Profile.class)
                .map(profile -> {
                    return postDao.findByProfileOrderByDateDesc(profile)
                            .stream().map(PostDto::new)
                            .collect(Collectors.toList());
                })
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Flux<PostDto> getUserPosts(String username) {
        return webClientBuilder.build().get().uri("http://user-service/api/profiles/" + username)
                .retrieve()
                .bodyToMono(Profile.class)
                .map(profile -> {
                    return postDao.findByProfileOrderByDateDesc(profile)
                            .stream().map(PostDto::new)
                            .collect(Collectors.toList());
                })
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Flux<PostTagDto> getAllPostTags() {
        return Mono.just(postTagDao.findAll())
                .flatMapMany(Flux::fromIterable)
                .map(postTag -> {
                    return new PostTagDto(postTag);
                });
    }

    @Override
    public Flux<PostTagDto> getBlockedPostTags(Authentication authentication) {
        UserInfo userInfo = userInfoDao.findByEmail(authentication.getPrincipal().toString())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Profile profile = profileDao.findByUser(userInfo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        return Mono.just(postTagDao.findAll())
                .flatMapMany(Flux::fromIterable)
                .map(postTag -> {
                    boolean isTagBlocked = profile.getBlockedTags().stream().anyMatch(postTag1 -> postTag1.getName().equals(postTag.getName()));
                    return new PostTagDto(postTag, isTagBlocked);
                });
    }

    @Override
    public Flux<PostTagDto> saveBlockedPostTags(SaveBlockedTagsDto dto, Authentication authentication) {
        UserInfo userInfo = userInfoDao.findByEmail(authentication.getPrincipal().toString())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Profile profile = profileDao.findByUser(userInfo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        postTagDao.deleteUsersByFirstName(profile.getId());

        profile.getBlockedTags().addAll(postTagDao.findAllById(dto.getTagsIds()));

        profileDao.save(profile);

        return Mono.just(profile.getBlockedTags())
                .flatMapMany(Flux::fromIterable)
                .map(PostTagDto::new);
    }

    @Override
    public Flux<PostDto> searchPosts(SearchRequestDto dto) {
        return Mono.just(postDao.findByText(dto.getText())).flatMapMany(Flux::fromIterable)
                .map(PostDto::new);
    }

}
