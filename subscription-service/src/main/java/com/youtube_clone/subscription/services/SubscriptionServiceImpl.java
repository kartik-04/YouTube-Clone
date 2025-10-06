package com.youtube_clone.subscription.services;

import com.youtube_clone.subscription.entities.Subscription;
import com.youtube_clone.subscription.repositories.SubscriptionRepository;
import com.youtube_clone.subscription.validation.SubscriptionValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository repository;
    private final SubscriptionValidator validator;

    public SubscriptionServiceImpl(SubscriptionRepository repository, SubscriptionValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }


    /** This method makes sure that user is subscribed to the creator.
     * We do not have the constructor with 4 values in the Subscription class,
     * that is the reason we are using the Builder pattern and
     * assigning the values at run time, and it adds them.
     * That is the reason we do not need to create manual constructor at the Subscription class.
     * The orElseGet() -> This method is Optional class method where it checks if there is already
     * subscribed user if there is already subscribed user, then it returns that user that moment.
     * If there is not any already subscribed, then it goes ahead and create new
     * subscriber and goes ahead with creating new subscribe
     * @param userId    unique id for the user
     * @param creatorId unique id for the creator
     * @return return the object of Subscription.
     */
    @Override
    public Subscription subscribe(UUID userId, UUID creatorId) {
        log.info("Attempting to subscribe user {} to creator {}", userId, creatorId);

        return repository.findByUserIdAndCreatorId(userId, creatorId)
                .map(existing -> {
                    // if already exists but inactive → reactivate
                    if (!existing.isActive()) {
                        existing.setActive(true);
                        existing.setSubscribedAt(LocalDateTime.now());
                        return repository.save(existing);
                    }
                    return existing; // already active
                })
                .orElseGet(() -> {
                    Subscription sub = Subscription.builder()
                            .userId(userId)
                            .creatorId(creatorId)
                            .subscribedAt(LocalDateTime.now())
                            .active(true)
                            .build();
                    validator.validate(sub);
                    log.info("User {} successfully subscribed to creator {}", userId, creatorId);
                    return repository.save(sub);
                });
    }

    /** This method gives back the paginated list of subscribers for the creator.
     * @param creatorId unique id for the creator.
     * @param page page number
     * @param size size of the page
     * @return Page of Subscriptions.
     */
    @Override
    public Page<Subscription> getSubscribersByCreatorId(UUID creatorId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByCreatorId(creatorId, pageable);
    }

    /** This method makes sure that we are getting the checking from the database
     * if the user is subscribed to the creator or not.
     * if a user is subscribed then we change the state to false,
     * it makes sure that entry is changed in the table
     * then we save this in table.
     * @param userId unique id of the user
     * @param creatorId unique id of the Creator.
     */
    @Override
    public void unsubscribe(UUID userId, UUID creatorId) {
        log.info("Attempting to unsubscribe user {} from creator {}", userId, creatorId);

        repository.findByUserIdAndCreatorId(userId, creatorId)
                .ifPresent(sub -> {
                    sub.setActive(false);
                    repository.save(sub);
                });
        log.info("User {} successfully unsubscribed from creator {}", userId, creatorId);

    }

    /** Gives back the list of Subscribers when provided with the userId
     * @param userId unique id for user.
     * @return List of Subscriptions.
     */
    @Override
    public List<Subscription> getSubscribersByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    /** Gives back the list of Subscribers for the creator.
     * @param creatorId unique id for the creator.
     * @return List of Subscriptions.
     */
    @Override
    public List<Subscription> getSubscribersByCreatorId(UUID creatorId) {
        return repository.findByCreatorId(creatorId);
    }

    /** It checks if the user has subscribed to the creator or not.
     * @param userId unique id for of the user.
     * @param creatorId unique id for the creator.
     * @return state true or false.
     */
    @Override
    public boolean isUserSubscribed(UUID userId, UUID creatorId) {
        return repository.findByUserIdAndCreatorId(userId, creatorId).isPresent();
    }

    /** Return the total number of subscribers as per given creatorId
     * @param creatorId unique id for the Creator
     * @return return the total count for subscriber
     */
    @Override
    public int getSubscriberCount(UUID creatorId) {
       return repository.countByCreatorIdAndActiveTrue(creatorId);
    }
}
