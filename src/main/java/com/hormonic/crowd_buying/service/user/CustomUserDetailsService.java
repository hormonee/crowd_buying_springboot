package com.hormonic.crowd_buying.service.user;

import com.hormonic.crowd_buying.domain.dto.CustomUserDetails;
import com.hormonic.crowd_buying.domain.entity.User;
import com.hormonic.crowd_buying.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User userData = userRepository.findByUserId(userId);

        if (userData != null) {
            // UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userData);
        }

        return null;
    }
}
