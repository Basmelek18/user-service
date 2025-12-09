package com.travel.userservice.repository;

import com.travel.userservice.enums.SubscriptionStatus;
import com.travel.userservice.model.Subscription;
import com.travel.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findBySubscriber(User subscriber);
    List<Subscription> findBySubscribedTo(User subscribedTo);
    boolean existsBySubscriberAndSubscribedToAndStatus(User subscriber, User subscribedTo, SubscriptionStatus status);
    Subscription findBySubscriberAndSubscribedTo(User subscriber, User subscribedTo);
    Subscription findByIdAndSubscriber(long id, User subscriber);
    Optional<Subscription> findByIdAndSubscribedTo(long id, User subscribedTo);
}
