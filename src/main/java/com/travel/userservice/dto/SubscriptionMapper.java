package com.travel.userservice.dto;

import com.travel.userservice.model.Subscription;
import com.travel.userservice.model.User;

public class SubscriptionMapper {
    public static SubscriptionDTO toSubscriptionDTO(Subscription subscription) {
        return SubscriptionDTO.builder()
                .subscriberId(subscription.getSubscriber().getId())
                .subscriberUsername(subscription.getSubscriber().getUsername())
                .subscribedToId(subscription.getSubscribedTo().getId())
                .subscribedToUsername(subscription.getSubscribedTo().getUsername())
                .status(subscription.getStatus())
                .createdAt(subscription.getCreatedAt())
                .build();
    }
}
