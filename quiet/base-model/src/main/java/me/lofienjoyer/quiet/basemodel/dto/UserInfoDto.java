package me.lofienjoyer.quiet.basemodel.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for a {@link me.lofienjoyer.quiet.basemodel.entity.UserInfo} entity
 */
@Data
public class UserInfoDto {

    private String email;

    private List<String> authorities;

    public UserInfoDto() {
        this.authorities = new ArrayList<>();
    }

}
