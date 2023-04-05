package me.lofienjoyer.quiet.basemodel.dto;

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
