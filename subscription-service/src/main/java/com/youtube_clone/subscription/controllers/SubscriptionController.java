package com.youtube_clone.subscription.controllers;

import com.youtube_clone.subscription.dtos.CreateSubscriptionRequest;
import com.youtube_clone.subscription.dtos.SubscriptionDTO;
import com.youtube_clone.subscription.entities.Subscription;
import com.youtube_clone.subscription.mappers.SubscriptionMapper;
import com.youtube_clone.subscription.services.SubscriptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subscription")
@Validated
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/creatorId")
    public ResponseEntity<SubscriptionDTO> subscribe(
            @PathVariable UUID creatorId,
            @RequestBody @Valid CreateSubscriptionRequest request
            ){

        Subscription sub = subscriptionService.subscribe(UUID.fromString(request.getUserId()), creatorId);
        return ResponseEntity.ok(SubscriptionMapper.toDTO(sub));
    }

    @DeleteMapping("/creatorId")
    public ResponseEntity<Void> unsubscribe(
            @PathVariable UUID userId,
            @RequestParam UUID creatorId
    ){
        subscriptionService.unsubscribe(userId, creatorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/Users/{userId}")
    public ResponseEntity<List<SubscriptionDTO>> getUserSubscriptions(@PathVariable UUID userId){
        List<SubscriptionDTO> subscriptions = subscriptionService.getSubscribersByUserId(userId)
                .stream().map(SubscriptionMapper:: toDTO)
                .toList();
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/Creators/{creatorId}/count")
    public ResponseEntity<Integer> getSubscriberCount(
            @PathVariable UUID creatorId
    ){
        int subscriptions = subscriptionService.getSubscriberCount(creatorId);
        return subscriptions > 0 ? ResponseEntity.ok().body(subscriptions) : ResponseEntity.notFound().build();
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isUserSubscribed(
            @RequestParam @NotNull(message = "UserId cannot be null") UUID userId,
            @RequestParam @NotNull(message = "CreatorId cannot be null") UUID creatorId){
        return ResponseEntity.ok(subscriptionService.isUserSubscribed(userId, creatorId));
    }
}
