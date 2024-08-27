package com.travel.userservice.service;

import com.travel.userservice.dto.NewUserRequest;
import com.travel.userservice.dto.ShortUserDTO;
import com.travel.userservice.dto.UserDTO;
import com.travel.userservice.dto.UserMapper;
import com.travel.userservice.model.User;
import com.travel.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createUser(NewUserRequest newUserRequest) {
        String username = newUserRequest.getUsername();

        User exceptionUser = userRepository.findByUsername(username);
        if (exceptionUser != null) {
            throw new UsernameNotFoundException("User already exits");
        }
        User user = new User();
        user.setUsername(newUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(newUserRequest.getPassword()));
        user.setTelegramId(newUserRequest.getTelegramId());
        user.setFirstName(newUserRequest.getFirstName());
        user.setLastName(newUserRequest.getLastName());
        userRepository.save(user);
        return UserMapper.toDTO(user);
    }

    @Transactional
    public ShortUserDTO getUser(long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exit");
        }
        return UserMapper.toShortDTO(user);
    }
}
