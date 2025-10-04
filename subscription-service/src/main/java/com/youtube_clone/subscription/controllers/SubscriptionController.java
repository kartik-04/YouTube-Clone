package com.youtube_clone.subscription.controllers;

import com.youtube_clone.subscription.dtos.CreateSubscriptionRequest;
import com.youtube_clone.subscription.dtos.GlobalResponse;
import com.youtube_clone.subscription.dtos.SubscriptionDTO;
import com.youtube_clone.subscription.entities.Subscription;
import com.youtube_clone.subscription.mappers.SubscriptionMapper;
import com.youtube_clone.subscription.services.SubscriptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subscription")
@Validated
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // ✅ 1️⃣ Subscribe to a creator
    @PostMapping("/{creatorId}")
    public ResponseEntity<GlobalResponse<SubscriptionDTO>> subscribe(
            @PathVariable UUID creatorId,
            @RequestBody @Valid CreateSubscriptionRequest request
            ){

        Subscription sub = subscriptionService.subscribe(UUID.fromString(request.getUserId()), creatorId);
        return ResponseEntity.ok(GlobalResponse.success("User subscribed successfully", SubscriptionMapper.toDTO(sub)));
    }

    // ✅ 2️⃣ Unsubscribe from a creator
    @DeleteMapping("/{creatorId}")
    public ResponseEntity<GlobalResponse<Void>> unsubscribe(
            @PathVariable UUID userId,
            @RequestParam UUID creatorId
    ){
        subscriptionService.unsubscribe(userId, creatorId);
        return ResponseEntity.ok(GlobalResponse.success("user unsubscribed successfully", null));
    }

    // ✅ 3️⃣ Get all subscriptions for a user
    @GetMapping("/users/{userId}")
    public ResponseEntity<GlobalResponse<List<SubscriptionDTO>>> getUserSubscriptions(@PathVariable UUID userId) {
        List<SubscriptionDTO> subscriptions = subscriptionService.getSubscribersByUserId(userId)
                .stream()
                .map(SubscriptionMapper::toDTO)
                .toList();

        return ResponseEntity.ok(
                GlobalResponse.success("Fetched user subscriptions successfully", subscriptions)
        );
    }

    // ✅ 4️⃣ Get total subscribers of a creator
    @GetMapping("/creators/{creatorId}/count")
    public ResponseEntity<GlobalResponse<Integer>> getSubscriberCount(@PathVariable UUID creatorId) {
        int count = subscriptionService.getSubscriberCount(creatorId);
        return ResponseEntity.ok(
                GlobalResponse.success("Fetched subscriber count successfully", count)
        );
    }

    // ✅ 5️⃣ Check if user is subscribed to creator
    @GetMapping("/check")
    public ResponseEntity<GlobalResponse<Boolean>> isUserSubscribed(
            @RequestParam @NotNull(message = "UserId cannot be null") UUID userId,
            @RequestParam @NotNull(message = "CreatorId cannot be null") UUID creatorId) {

        boolean isSubscribed = subscriptionService.isUserSubscribed(userId, creatorId);
        return ResponseEntity.ok(
                GlobalResponse.success("Subscription status fetched successfully", isSubscribed)
        );
    }
}
