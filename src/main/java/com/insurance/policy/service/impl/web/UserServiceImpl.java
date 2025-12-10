package com.insurance.policy.service.impl.web;

import com.insurance.policy.data.entity.User;
import com.insurance.policy.data.repository.UserRepository;
import com.insurance.policy.exception.WebException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings({
        "PMD.AvoidUncheckedExceptionsInSignatures"})
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public User getUserByUserId(String requestId, String userId) throws WebException {
        log.info("[RequestId: {}] Execute UserServiceImpl.getUserByUserId()", requestId);
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new WebException("User not found"));
    }

    public User getUserByEmail(String requestId, String email) throws WebException {
        log.info("[RequestId: {}] Execute UserServiceImpl.getUserByEmail()", requestId);

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new WebException("User not found: " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NullPointerException("User not found: " + email));

        org.springframework.security.core.userdetails.User.UserBuilder builder
                = org.springframework.security.core.userdetails.User.withUsername(user.getEmail());
        builder.password(user.getPassword());
        builder.roles("USER");

        return builder.build();
    }
}