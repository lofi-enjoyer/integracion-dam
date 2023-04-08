package me.lofienjoyer.quiet.basemodel.dto;

import lombok.Data;
import me.lofienjoyer.quiet.basemodel.entity.Post;

import java.util.Date;

@Data
public class PostDto {

    private String content;
    private String profileUsername;
    private Date date;
    private int likes;

    public PostDto(Post post) {
        this.content = post.getContent();
        this.profileUsername = post.getProfile().getUsername();
        this.date = post.getDate();
        this.likes = post.getLikes().size();
    }

}
