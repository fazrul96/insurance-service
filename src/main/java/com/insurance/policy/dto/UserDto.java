package com.insurance.policy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private Long id;
    private String userId;

    private String username;
    @Email
    @NotNull
    private String email;
    private String password;
    private String role;
}