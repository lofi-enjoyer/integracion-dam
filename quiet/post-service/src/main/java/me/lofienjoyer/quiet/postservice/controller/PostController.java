package me.lofienjoyer.quiet.postservice.controller;

import me.lofienjoyer.quiet.basemodel.dao.PostDao;
import me.lofienjoyer.quiet.basemodel.dto.CreatePostDto;
import me.lofienjoyer.quiet.basemodel.dto.PostDto;
import me.lofienjoyer.quiet.basemodel.entity.Post;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    PostDao postDao;

    @PostMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public Mono<Post> createPost(@RequestBody CreatePostDto dto, Authentication authentication) {
        return webClientBuilder.build().get().uri("http://user-service/api/profiles/me")
                .cookie("token", authentication.getCredentials().toString())
                .retrieve()
                .bodyToMono(Profile.class)
                .map(profile -> {
                    Post post = new Post();
                    post.setContent(dto.getContent());
                    post.setDate(new Date());
                    post.setProfile(profile);

                    return postDao.save(post);
                });
    }

    @GetMapping("/feed")
    @PreAuthorize("isAuthenticated()")
    public Flux<PostDto> getFeed(Authentication authentication) {
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

}
