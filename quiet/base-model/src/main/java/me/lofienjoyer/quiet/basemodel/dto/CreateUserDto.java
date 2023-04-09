package me.lofienjoyer.quiet.basemodel.dto;

import lombok.Data;

/**
 * DTO for a request to create a new user
 */
@Data
public class CreateUserDto {

    private String email;
    private String password;
    private String username;

}
