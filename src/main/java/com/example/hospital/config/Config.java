package com.example.hospital.config;

import com.example.hospital.Entity.User;
import com.example.hospital.Entity.type.RoleType;
import com.example.hospital.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public ApplicationRunner createAdminUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.username}") String username,
            @Value("${app.admin.password}") String password
    ) {
        return args -> {
            if (userRepository.findByUsername(username).isEmpty()) {
                userRepository.save(User.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .roles(Set.of(RoleType.ADMIN))
                        .build());
            }
        };
    }

}
