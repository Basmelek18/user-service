package com.travel.userservice.controller;

import com.travel.userservice.dto.SubscriptionDTO;
import com.travel.userservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/{subId}/")
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionDTO getSubscription(
            @PathVariable long subId,
            Authentication authentication
    ) {
        return subscriptionService.getSubscription(subId, authentication.getName());
    }

    @PostMapping("/{subId}/accept")
    @ResponseStatus(HttpStatus.CREATED)
    public void acceptSubscription(
            @PathVariable long subId,
            Authentication authentication
    ) {
        subscriptionService.acceptSubscription(subId, authentication.getName());
    }

    @PostMapping("/{subId}/reject")
    @ResponseStatus(HttpStatus.CREATED)
    public void rejectSubscription(
            @PathVariable long subId,
            Authentication authentication
    ) {
        subscriptionService.rejectSubscription(subId, authentication.getName());
    }

    @PostMapping("/{subId}/delete")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteSubscription(
            @PathVariable long subId,
            Authentication authentication
    ) {
        subscriptionService.deleteSubscription(subId, authentication.getName());
    }
}
