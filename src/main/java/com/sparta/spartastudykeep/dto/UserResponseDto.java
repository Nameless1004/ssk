package com.sparta.spartastudykeep.dto;

import com.sparta.spartastudykeep.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDto {
    private long id;
    private String username;
    private String password;
    private String description;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.description = user.getDescription();
        this.enabled = user.getEnabled();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }
}
