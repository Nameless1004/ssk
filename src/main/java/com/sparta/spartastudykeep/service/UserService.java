package com.sparta.spartastudykeep.service;

import com.sparta.spartastudykeep.dto.PasswordRequestDto;
import com.sparta.spartastudykeep.dto.UserRequestDto;
import com.sparta.spartastudykeep.dto.UserResponseDto;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(requestDto.getPassword());
        user.setDescription(requestDto.getDescription());
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User with id " + id + " not found")
        );
        return new UserResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users) {
            if(user.getEnabled()) userResponseDtos.add(new UserResponseDto(user));
        }
        return userResponseDtos;
    }

    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User with id " + id + " not found")
        );
        user.setDescription(requestDto.getDescription());
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }


    public Long deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User with id " + id + " not found")
        );
        user.setEnabled(false);
        userRepository.save(user);
        return user.getId();
    }

    public UserResponseDto updatePassword(Long id, PasswordRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User with id " + id + " not found")
        );
        if(user.getPassword().equals(requestDto.getPassword())){
            user.setPassword(requestDto.getNewPassword());
        }
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }
}
