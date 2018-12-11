package com.jira.demo.service;

import com.jira.demo.model.User;
import com.jira.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format("Can not find user by name [%s]", s)
                        )
                );
    }

    public User loadUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                String.format("Can not find user by name [%s]", id)
                        )
                );
    }
}
