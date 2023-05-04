package me.lofienjoyer.quiet.basemodel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDto {

    private String username;

    public RegisterResponseDto(UserInfo userInfo) {
        this.username = userInfo.getProfile().getUsername();
    }

}
