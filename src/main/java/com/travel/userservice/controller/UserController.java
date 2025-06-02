package com.travel.userservice.controller;

import com.travel.userservice.dto.ShortUserDTO;
import com.travel.userservice.dto.SubscriptionDTO;
import com.travel.userservice.dto.UserDTO;
import com.travel.userservice.dto.UserInfoDTO;
import com.travel.userservice.service.SubscriptionService;
import com.travel.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/users")
public class UserController {
    private final UserService userService;

    private final SubscriptionService subscriptionService;

    @PostMapping("/{userId}/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribe(
            @PathVariable long userId,
            Authentication authentication
    ) {
        subscriptionService.subscribe(userId, authentication.getName());
    }

    @PostMapping("/{userId}/unsubscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public void unsubscribe(
            @PathVariable long userId,
            Authentication authentication
    ) {
        subscriptionService.unsubscribe(userId, authentication.getName());
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ShortUserDTO getUser(@PathVariable long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getMe(Authentication authentication) {
        return userService.getMe(authentication.getName());
    }

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO updateMe(@RequestBody UserInfoDTO userInfoDTO, Authentication authentication) {
        return userService.updateUser(userInfoDTO, authentication.getName());
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        boolean isDeleted = userService.deleteUser(authentication.getName());
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/me/subscriptions")
    @ResponseStatus(HttpStatus.OK)
    public List<SubscriptionDTO> mySubscriptions(Authentication authentication) {
        return subscriptionService.getMySubscriptions(authentication.getName());
    }

    @GetMapping("/me/followers")
    @ResponseStatus(HttpStatus.OK)
    public List<SubscriptionDTO> myFollowers(Authentication authentication) {
        return subscriptionService.getMySubscribers(authentication.getName());
    }
}
