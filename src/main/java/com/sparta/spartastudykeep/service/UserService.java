package com.sparta.spartastudykeep.service;

import com.sparta.spartastudykeep.common.enums.UserRole;
import com.sparta.spartastudykeep.dto.*;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.BookmarkRepository;
import com.sparta.spartastudykeep.repository.UserRepository;
import com.sparta.spartastudykeep.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final FriendshipService friendshipService;
    private final PasswordEncoder passwordEncoder;
    @Value("${admin.token}")
    private String adminToken;

//    public UserResponseDto createUser(UserRequestDto requestDto) {
//        User user = new User();
//        user.setUsername(requestDto.getUsername());
//        user.setPassword(requestDto.getPassword());
//        user.setDescription(requestDto.getDescription());
//        User savedUser = userRepository.save(user);
//
//        return new UserResponseDto(savedUser);
//    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(UserDetailsImpl userDetails, Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User with id " + id + " not found")
        );
        if(Objects.equals(userDetails.getUser().getId(), id)) return new UserResponseDto(user);
        else {
            // 다른 유저의 정보를 상세조회할 경우 비밀번호 모자이크 처리.
            user.setPassword("********");
            return new UserResponseDto(user);
        }
    }

    @Transactional(readOnly = true)
    public List<UsersResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UsersResponseDto> usersResponseDto = new ArrayList<>();
        for (User user : users) {
            if(user.getEnabled()) usersResponseDto.add(new UsersResponseDto(user));
        }
        return usersResponseDto;
    }

    public UserResponseDto updateUser(UserDetailsImpl userDetails, UserRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("User with id " + userDetails.getUser().getId() + " not found")
        );
        user.setDescription(requestDto.getDescription());
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    public Long deleteUser(UserDetailsImpl userDetails, DeleteUserRequestDto requestDto) {
        if(passwordEncoder.matches(requestDto.getPassword(), userDetails.getPassword())) {
            User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                    () -> new IllegalArgumentException("User with id " + userDetails.getUser().getId() + " not found")
            );
            user.setEnabled(false);

            // 유저 삭제 시 북마크, 친구 삭제
            friendshipService.removeAllFriendship(user);
            bookmarkRepository.deleteAllByUser(user);

            userRepository.save(user);
            return user.getId();
        }
        else {
            throw new IllegalArgumentException("Wrong password");
        }
    }

    public UserResponseDto updatePassword(UserDetailsImpl userDetails, PasswordRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("User with id " + userDetails.getUser().getId() + " not found")
        );
        if(passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
            User savedUser = userRepository.save(user);
            return new UserResponseDto(savedUser);
        }
        else {
            new IllegalArgumentException("Password doesn't match");
            return null;
        }
    }

    public UserResponseDto createUser(SignupRequestDto requestDto) {
        UserRole role = UserRole.USER;
        if(requestDto.isAdmin()){
            if(requestDto.getAdminToken().equals(adminToken)){
                role = UserRole.ADMIN;
            } else {
                throw new IllegalArgumentException("잘못된 어드민 토큰입니다.");
            }
        }

        boolean isExistsEmail = userRepository.existsByEmail(requestDto.getEmail());

        if(isExistsEmail){
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        String password = passwordEncoder.encode(requestDto.getPassword());
        User newUser = new User(requestDto.getName(), requestDto.getEmail(), password, requestDto.getDescription(), true, role);
        userRepository.save(newUser);

        return new UserResponseDto(newUser);
    }

    public UserResponseDto getUser(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return new UserResponseDto(user);

    }
}
