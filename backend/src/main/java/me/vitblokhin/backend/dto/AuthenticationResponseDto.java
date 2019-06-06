package me.vitblokhin.backend.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {
    private String username;
    private String token;
} // class AuthenticationResponseDto
