package me.lofienjoyer.quiet.basemodel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.lofienjoyer.quiet.basemodel.entity.Profile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    private long id;
    private String name;
    private String username;
    private String description;

    public ProfileDto(Profile profile) {
        this.id = profile.getId();
        this.name = profile.getName();
        this.username = profile.getUsername();
        this.description = profile.getDescription();
    }

}
