package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.dto.request.notification.CreateNotificationRequest;
import com.hormonic.crowd_buying.domain.dto.response.notification.GetNotificationListOfUserResponse;
import com.hormonic.crowd_buying.domain.entity.Notification;
import com.hormonic.crowd_buying.service.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Tag(name = "Notification", description = "Notification API")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{id}")
    @Operation(summary = "사용자 알림 조회", description = "사용자 ID를 통해 해당 사용자의 모든 알림 조회")
    public ResponseEntity<List<GetNotificationListOfUserResponse>> getNotificationListByUserId(@PathVariable("id") String userId) {
        // 확인한 것과 확인 안 한 것 구별되게 프론트 측에서 checkDate 보고 처리 필요
        return ResponseEntity.ok(notificationService.getNotificationListOfUser(userId));
    }

    @PostMapping
    @Operation(summary = "알림 보내기", description = "알림을 보낼 사용자 ID와 제목, 내용을 받아 알림 생성")
    public ResponseEntity<Notification> sendNotification(@RequestBody @Valid CreateNotificationRequest createNotificationRequest) {
        notificationService.createNotification(createNotificationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{num}")
    @Operation(summary = "알림 확인 처리", description = "알림 번호를 통해 해당 알림 확인 일자를 추가")
    public ResponseEntity<Notification> checkNotification(@PathVariable("num") int notificationNo) {
        notificationService.checkNotification(notificationNo);
        return ResponseEntity.ok().build();
    }

}
