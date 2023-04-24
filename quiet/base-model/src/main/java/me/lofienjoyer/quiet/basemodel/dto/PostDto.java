package me.lofienjoyer.quiet.basemodel.dto;

import lombok.Data;
import me.lofienjoyer.quiet.basemodel.entity.Post;
import me.lofienjoyer.quiet.basemodel.utils.DateUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for a {@link me.lofienjoyer.quiet.basemodel.entity.Post} entity
 */
@Data
public class PostDto {

    private String content;
    private String profileUsername;
    private String profileName;
    private String date;
    private int likes;
    private Set<PostTagDto> tags;
    private boolean blurred;

    public PostDto(Post post) {
        this.content = post.getContent();
        this.profileUsername = post.getProfile().getUsername();
        this.profileName = post.getProfile().getName();
        this.date = DateUtils.postDateFormat.format(post.getDate());
        this.likes = post.getLikes().size();
        this.tags = post.getTags().stream()
                .map(PostTagDto::new)
                .collect(Collectors.toSet());
        this.blurred = post.getTags().stream()
                .anyMatch(postTag -> post.getProfile().getBlockedTags().contains(postTag));
    }

}
