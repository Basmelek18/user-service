package com.travel.userservice.service;

import com.travel.userservice.dto.SubscriptionDTO;
import com.travel.userservice.dto.SubscriptionMapper;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(String currentUsername) {
        return userRepository.findByUsername(currentUsername);
    }

    @Transactional
    public SubscriptionDTO getSubscription(long subId, String currentUsername) {
        User currentUser = getCurrentUser(currentUsername);
        Subscription subscription = subscriptionRepository
                .findByIdAndSubscribedTo(subId, currentUser)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));
        return SubscriptionMapper.toSubscriptionDTO(subscription);
    }

    @Transactional
    public List<SubscriptionDTO> getMySubscriptions(String currentUsername) {
        User currentUser = getCurrentUser(currentUsername);
        return subscriptionRepository.findBySubscriber(currentUser)
                .stream()
                .map(SubscriptionMapper::toSubscriptionDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<SubscriptionDTO> getMySubscribers(String currentUsername) {
        User currentUser = getCurrentUser(currentUsername);
        return subscriptionRepository.findBySubscribedTo(currentUser)
                .stream()
                .map(SubscriptionMapper::toSubscriptionDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void subscribe(long id, String currentUsername) {
        User currentUser = getCurrentUser(currentUsername);
        User subscribedTo = userRepository.findById(id);
        if (subscribedTo == null) {
            throw new NotFoundException("User doesn't exist");
        }
        if (subscriptionRepository.existsBySubscriberAndSubscribedToAndStatus(currentUser, subscribedTo, SubscriptionStatus.ACCEPTED)
                || subscriptionRepository.existsBySubscriberAndSubscribedToAndStatus(currentUser, subscribedTo, SubscriptionStatus.PENDING)) {
            throw new AlreadyExistException("Subscription is already exist");
        }
        if (currentUser.equals(subscribedTo)) {
            throw new SubscriptionToYourselfException("Cannot subscribe to yourself");
        }
        Subscription subscription = new Subscription();
        subscription.setSubscriber(currentUser);
        subscription.setSubscribedTo(subscribedTo);
        subscription.setStatus(SubscriptionStatus.PENDING);
        subscription.setCreatedAt(LocalDateTime.now());
        subscriptionRepository.save(subscription);
    }


    @Transactional
    public void unsubscribe(long id, String currentUsername) {
        User currentUser = getCurrentUser(currentUsername);
        User subscribedTo = userRepository.findById(id);
        if (subscriptionRepository.existsBySubscriberAndSubscribedToAndStatus(currentUser, subscribedTo, SubscriptionStatus.ACCEPTED)
                || subscriptionRepository.existsBySubscriberAndSubscribedToAndStatus(currentUser, subscribedTo, SubscriptionStatus.PENDING)) {
            Subscription subscription = subscriptionRepository.findBySubscriberAndSubscribedTo(currentUser, subscribedTo);
            subscription.setStatus(SubscriptionStatus.UNSUBSCRIBED);
            subscriptionRepository.save(subscription);
        } else {
            throw new NotFoundException("Subscription not found");
        }
    }


    @Transactional
    public void acceptSubscription(long subId, String currentUsername) {
        User currentUser = getCurrentUser(currentUsername);
        Subscription subscription = subscriptionRepository
                .findByIdAndSubscribedTo(subId, currentUser)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));
        if (subscription.getStatus() == SubscriptionStatus.PENDING) {
            subscription.setStatus(SubscriptionStatus.ACCEPTED);
            subscriptionRepository.save(subscription);
        }
    }

    @Transactional
    public void rejectSubscription(long subId, String currentUsername) {
        User currentUser = getCurrentUser(currentUsername);
        Subscription subscription = subscriptionRepository
                .findByIdAndSubscribedTo(subId, currentUser)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));
        if (subscription.getStatus() == SubscriptionStatus.PENDING) {
            subscription.setStatus(SubscriptionStatus.REJECTED);
            subscriptionRepository.save(subscription);
        }
    }

    @Transactional
    public void deleteSubscription(long subId, String currentUsername) {
        User currentUser = getCurrentUser(currentUsername);
        Subscription subscription = subscriptionRepository
                .findByIdAndSubscribedTo(subId, currentUser)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));
        if (subscription.getStatus() == SubscriptionStatus.ACCEPTED) {
            subscription.setStatus(SubscriptionStatus.UNSUBSCRIBED);
            subscriptionRepository.save(subscription);
        }
    }
}
