package com.hormonic.crowd_buying.repository;

import com.hormonic.crowd_buying.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByUserIdOrderByNotificationSendDateDesc(String userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Notification n set n.notificationCheckDate = now() where n.notificationNo = :notificationNo")
    void checkNotification(@Param("notificationNo") int notificationNo);

}
