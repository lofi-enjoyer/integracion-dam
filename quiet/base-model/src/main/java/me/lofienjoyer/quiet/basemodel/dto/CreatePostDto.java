package me.lofienjoyer.quiet.basemodel.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO for a request to create a new post
 */
@Data
public class CreatePostDto {

    private String content;
    private List<Long> tagIds;

}
