package me.lofienjoyer.quiet.basemodel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private boolean success;
    private String message;

}
