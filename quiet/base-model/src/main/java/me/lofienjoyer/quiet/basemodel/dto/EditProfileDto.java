package me.lofienjoyer.quiet.basemodel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileDto {

    private String name;
    private String username;
    private String description;

}
