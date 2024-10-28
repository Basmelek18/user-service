package com.travel.userservice.service;

import com.travel.userservice.dto.*;
import com.travel.userservice.exception.NotFoundException;
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
    private final UserEventProducer userEventProducer;

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
        userEventProducer.sendUserEvent(new UserEvent(username, "created"));
        return UserMapper.toDTO(user);
    }

    @Transactional
    public ShortUserDTO getUser(long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("User doesn't exit");
        }
        return UserMapper.toShortDTO(user);
    }

    @Transactional
    public UserDTO getMe(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User doesn't exit");
        }
        return UserMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateUser(UserInfoDTO userInfoDTO, String currentUsername) {
        User user = userRepository.findByUsername(currentUsername);
        if (user == null) {
            throw new NotFoundException("User doesn't exit");
        }
        user.setFirstName(userInfoDTO.getFirstName());
        user.setLastName(userInfoDTO.getLastName());
        user.setTelegramId(userInfoDTO.getTelegramId());
        userRepository.save(user);
        return UserMapper.toDTO(user);
    }

    @Transactional
    public boolean deleteUser(String currentUsername) {
        long id = userRepository.findByUsername(currentUsername).getId();
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            userEventProducer.sendUserEvent(new UserEvent(currentUsername, "deleted"));
            return true;
        }
        return false;
    }

}
