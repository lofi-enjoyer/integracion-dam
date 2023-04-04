package me.lofienjoyer.quiet.auth.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfoDto {

    private String email;

    private List<String> authorities;

    public UserInfoDto() {
        this.authorities = new ArrayList<>();
    }

}
