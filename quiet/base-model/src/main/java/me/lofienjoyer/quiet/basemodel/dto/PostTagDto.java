package me.lofienjoyer.quiet.basemodel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.lofienjoyer.quiet.basemodel.entity.PostTag;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTagDto {

    private String name;
    private String hexColor;

    public PostTagDto(PostTag postTag) {
        this.name = postTag.getName();
        this.hexColor = postTag.getHexColor();
    }

}
