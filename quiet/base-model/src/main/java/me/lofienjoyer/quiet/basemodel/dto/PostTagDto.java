package me.lofienjoyer.quiet.basemodel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.lofienjoyer.quiet.basemodel.entity.PostTag;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTagDto {

    private long id;
    private String name;
    private String hexColor;
    private boolean checked;

    public PostTagDto(PostTag postTag) {
        this(postTag, false);
    }

    public PostTagDto(PostTag postTag, boolean checked) {
        this.id = postTag.getId();
        this.name = postTag.getName();
        this.hexColor = postTag.getHexColor();
        this.checked = checked;
    }

}
