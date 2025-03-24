package com.ashgrid.qrcodeapp.services;


import com.ashgrid.qrcodeapp.dto.LoginRequest;
import com.ashgrid.qrcodeapp.dto.SignupRequest;
import com.ashgrid.qrcodeapp.entities.User;
import com.ashgrid.qrcodeapp.repositories.UserRepository;
import com.ashgrid.qrcodeapp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;


    public void signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        User user = new User();
        user.setPhoneId(request.getPhoneId());
        user.setFullName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(User.Role.SUPER_ADMIN);
        user.setAttendances(Collections.emptyList());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

       if(!user.getPhoneId().equals(request.getPhoneId())){
           throw new BadCredentialsException("phone not registered");
       }

        String token = jwtUtil.generateToken(user);
        System.out.println("Generated Token: " + token);
        return token;
    }


    public void changeUserRole(Long userId, User.Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(newRole);
        userRepository.save(user);
    }


    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())) // Convert role to authority
        );
    }
}
