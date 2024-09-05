package com.sparta.spartastudykeep.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]:;\"'|<>,.?/~`]).{8,}$",
        message = "대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야합니다.(8자 이상)")
    private String password;

    private String description;

    private boolean admin;
    private final String adminToken = "";
}
