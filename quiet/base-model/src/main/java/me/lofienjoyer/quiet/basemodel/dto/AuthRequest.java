package me.lofienjoyer.quiet.basemodel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for a request to log-in/authenticate a user
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String email, password;

}
