package com.sparta.spartastudykeep.user.dto;

import com.sparta.spartastudykeep.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private long id;
    private String username;
    private String password;
    private String description;
    private String role;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole()
            .getAuthority();
        this.description = user.getDescription();
        this.enabled = user.getEnabled();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }
}
