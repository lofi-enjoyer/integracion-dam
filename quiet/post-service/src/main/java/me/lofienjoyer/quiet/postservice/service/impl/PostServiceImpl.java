package me.lofienjoyer.quiet.postservice.service.impl;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dao.PostDao;
import me.lofienjoyer.quiet.basemodel.dto.CreatePostDto;
import me.lofienjoyer.quiet.basemodel.dto.PostDto;
import me.lofienjoyer.quiet.basemodel.entity.Post;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.postservice.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * Implementation of {@link PostService}
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostDao postDao;

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<PostDto> createPost(CreatePostDto dto, Authentication authentication) {
        return webClientBuilder.build().get().uri("http://user-service/api/profiles/me")
                .cookie("token", authentication.getCredentials().toString())
                .retrieve()
                .bodyToMono(Profile.class)
                .map(profile -> {
                    Post post = new Post();
                    post.setContent(dto.getContent());
                    post.setDate(new Date());
                    post.setProfile(profile);
                    post = postDao.save(post);

                    return new PostDto(post);
                });
    }

    @Override
    public Flux<PostDto> getCurrentUserFeed(Authentication authentication) {
        return webClientBuilder.build().get().uri("http://user-service/api/profiles/me")
                .cookie("token", authentication.getCredentials().toString())
                .retrieve()
                .bodyToMono(Profile.class)
                .map(profile -> {
                    return postDao.getPostsFromFollowed(profile.getId())
                            .stream().map(PostDto::new)
                            .collect(Collectors.toList());
                })
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Flux<PostDto> getUserFeed(String username) {
        return webClientBuilder.build().get().uri("http://user-service/api/profiles/" + username)
                .retrieve()
                .bodyToMono(Profile.class)
                .map(profile -> {
                    return postDao.getPostsFromFollowed(profile.getId())
                            .stream().map(PostDto::new)
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

}
