package com.hormonic.crowd_buying.service.notification;

import com.hormonic.crowd_buying.domain.dto.request.notification.CreateNotificationRequest;
import com.hormonic.crowd_buying.domain.dto.response.notification.GetNotificationListOfUserResponse;
import com.hormonic.crowd_buying.domain.entity.Notification;
import com.hormonic.crowd_buying.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public List<GetNotificationListOfUserResponse> getNotificationListOfUser(String userId) {
        return notificationRepository.findAllByUserIdOrderByNotificationSendDateDesc(userId).stream()
                .map(i -> i.toGetNotificationListOfUserResponse(i))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createNotification(CreateNotificationRequest createNotificationRequest) {
        notificationRepository.save(Notification.builder()
                                                .userId(createNotificationRequest.getUserId())
                                                .notificationTitle(createNotificationRequest.getNotificationTitle())
                                                .notificationContent(createNotificationRequest.getNotificationContent())
                                                .build());
    }

    @Transactional
    public void checkNotification(int notificationNo) {
        notificationRepository.checkNotification(notificationNo);
    }

}
