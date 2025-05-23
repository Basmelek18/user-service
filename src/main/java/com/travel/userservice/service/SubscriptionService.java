package com.travel.userservice.service;

import com.travel.userservice.enums.SubscriptionStatus;
import com.travel.userservice.exception.AlreadyExistException;
import com.travel.userservice.exception.NotFoundException;
import com.travel.userservice.exception.SubscriptionToYourselfException;
import com.travel.userservice.model.Subscription;
import com.travel.userservice.model.User;
import com.travel.userservice.repository.SubscriptionRepository;
import com.travel.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Transactional
    public void subscribe(long id, String currentUsername) {
        User subscriber = userRepository.findByUsername(currentUsername);
        User subscribedTo = userRepository.findById(id);
        if (subscribedTo == null) {
            throw new NotFoundException("User doesn't exist");
        }
        if (subscriptionRepository.existsBySubscriberAndSubscribedToAndStatus(subscriber, subscribedTo, SubscriptionStatus.ACCEPTED)
                || subscriptionRepository.existsBySubscriberAndSubscribedToAndStatus(subscriber, subscribedTo, SubscriptionStatus.PENDING)) {
            throw new AlreadyExistException("Subscription is already exist");
        }
        if (subscriber.equals(subscribedTo)) {
            throw new SubscriptionToYourselfException("Cannot subscribe to yourself");
        }
        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setSubscribedTo(subscribedTo);
        subscription.setStatus(SubscriptionStatus.PENDING);
        subscription.setCreatedAt(LocalDateTime.now());
        subscriptionRepository.save(subscription);
    }
}
