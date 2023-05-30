package me.lofienjoyer.quiet.basemodel.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaveBlockedTagsDto {

    private List<Long> tagsIds;

}
