package com.travel.userservice.dto;

import com.travel.userservice.model.Subscription;

public class SubscriptionMapper {
    public static SubscriptionDTO toSubscriptionDTO(Subscription subscription) {
        return SubscriptionDTO.builder()
                .id(subscription.getId())
                .subscriberId(subscription.getSubscriber().getId())
                .subscriberUsername(subscription.getSubscriber().getUsername())
                .subscribedToId(subscription.getSubscribedTo().getId())
                .subscribedToUsername(subscription.getSubscribedTo().getUsername())
                .status(subscription.getStatus())
                .createdAt(subscription.getCreatedAt())
                .build();
    }


}
