package com.sparta.spartastudykeep.security;

import com.sparta.spartastudykeep.common.exception.UserEmailNotFoundException;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new UserEmailNotFoundException(userEmail));
        return new UserDetailsImpl(user);
    }
}
