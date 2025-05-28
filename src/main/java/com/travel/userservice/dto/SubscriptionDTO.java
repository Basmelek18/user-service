package com.travel.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.travel.userservice.enums.SubscriptionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class SubscriptionDTO {
    @JsonProperty("id")
    private final Long id;
    @JsonProperty("subscriber_id")
    private final Long subscriberId;
    @JsonProperty("subscriber_username")
    private final String subscriberUsername;

    @JsonProperty("subscribed_to_id")
    private final Long subscribedToId;
    @JsonProperty("subscriber_username")
    private final String subscribedToUsername;

    @JsonProperty("status")
    private final SubscriptionStatus status;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;
}
