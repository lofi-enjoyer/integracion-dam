package me.lofienjoyer.quiet.auth.dto;

import lombok.Data;

@Data
public class GetTokenDto {

    private String email, password;

}
