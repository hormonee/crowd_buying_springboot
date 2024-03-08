package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.dto.request.notification.CreateNotificationRequest;
import com.hormonic.crowd_buying.domain.dto.response.notification.GetNotificationListOfUserResponse;
import com.hormonic.crowd_buying.domain.entity.Notification;
import com.hormonic.crowd_buying.service.notification.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{id}")
    public ResponseEntity<List<GetNotificationListOfUserResponse>> getNotificationListByUserId(@PathVariable("id") String userId) {
        // 확인한 것과 확인 안 한 것 구별되게 프론트 측에서 checkDate 보고 처리 필요
        return ResponseEntity.ok(notificationService.getNotificationListOfUser(userId));
    }

    @PostMapping
    public ResponseEntity<Notification> sendNotification(@RequestBody @Valid CreateNotificationRequest createNotificationRequest) {
        notificationService.createNotification(createNotificationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{num}")
    public ResponseEntity<Notification> checkNotification(@PathVariable("num") int notificationNo) {
        notificationService.checkNotification(notificationNo);
        return ResponseEntity.ok().build();
    }

}
