package com.travel.userservice.model;

import com.travel.userservice.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
@Getter
@Setter
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private User subscriber;

    @ManyToOne
    @JoinColumn(name = "subscribed_to_id")
    private User subscribedTo;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status; // PENDING, ACCEPTED, REJECTED

    private LocalDateTime createdAt;
}
