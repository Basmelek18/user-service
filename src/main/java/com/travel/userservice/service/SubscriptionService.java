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
import java.util.Objects;

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


    @Transactional
    public void unsubscribe(long id, String currentUsername) {
        User subscriber = userRepository.findByUsername(currentUsername);
        User subscribedTo = userRepository.findById(id);
        if (subscriptionRepository.existsBySubscriberAndSubscribedToAndStatus(subscriber, subscribedTo, SubscriptionStatus.ACCEPTED)
                || subscriptionRepository.existsBySubscriberAndSubscribedToAndStatus(subscriber, subscribedTo, SubscriptionStatus.PENDING)) {
            Subscription subscription = subscriptionRepository.findBySubscriberAndSubscribedTo(subscriber, subscribedTo);
            subscription.setStatus(SubscriptionStatus.UNSUBSCRIBED);
            subscriptionRepository.save(subscription);
        } else {
            throw new NotFoundException("Subscription not found");
        }
    }


    @Transactional
    public void acceptSubscription(long subId, String currentUsername) {
        Subscription subscription = subscriptionRepository
                .findById(subId)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));
        if (Objects.equals(subscription.getSubscribedTo().getUsername(), currentUsername)) {
            subscription.setStatus(SubscriptionStatus.ACCEPTED);
            subscriptionRepository.save(subscription);
        } else {
            throw new NotFoundException("Subscription not found");
        }
    }
}
