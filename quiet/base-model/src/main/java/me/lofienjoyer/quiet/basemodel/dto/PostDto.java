package me.lofienjoyer.quiet.basemodel.dto;

import lombok.Data;
import me.lofienjoyer.quiet.basemodel.entity.Post;
import me.lofienjoyer.quiet.basemodel.utils.DateUtils;

/**
 * DTO for a {@link me.lofienjoyer.quiet.basemodel.entity.Post} entity
 */
@Data
public class PostDto {

    private String content;
    private String profileUsername;
    private String date;
    private int likes;

    public PostDto(Post post) {
        this.content = post.getContent();
        this.profileUsername = post.getProfile().getUsername();
        this.date = DateUtils.postDateFormat.format(post.getDate());
        this.likes = post.getLikes().size();
    }

}
