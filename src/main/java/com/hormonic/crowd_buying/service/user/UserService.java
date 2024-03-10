package com.hormonic.crowd_buying.service.user;

import com.hormonic.crowd_buying.domain.dto.request.user.CreateUserRequest;
import com.hormonic.crowd_buying.domain.dto.request.user.UpdateUserRequest;
import com.hormonic.crowd_buying.domain.dto.response.user.CreateAndDeleteUserResponse;
import com.hormonic.crowd_buying.domain.entity.Notification;
import com.hormonic.crowd_buying.domain.entity.User;
import com.hormonic.crowd_buying.repository.NotificationRepository;
import com.hormonic.crowd_buying.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    // 회원 가입
    @Transactional
    public CreateAndDeleteUserResponse createUser(CreateUserRequest createUserRequest) {
        User newUser = new User(
                createUserRequest.getUserId(),
                createUserRequest.getUserPw(),
                createUserRequest.getUserName(),
                createUserRequest.getUserBirth(),
                createUserRequest.getUserContact(),
                createUserRequest.getUserAddress(),
                createUserRequest.getUserEmail(),
                createUserRequest.getUserGender());
        CreateAndDeleteUserResponse createAndDeleteUserResponse = userRepository.save(newUser).toCreateAndDeleteUserResponse();

        notificationRepository.save( Notification.builder()
                                                .userId(createAndDeleteUserResponse.getUserId())
                                                .notificationTitle("회원가입을 환영합니다!")
                                                .notificationContent(createAndDeleteUserResponse.getUserName()
                                                        + "(" + createAndDeleteUserResponse.getUserId() + ")"
                                                        + "님 성공적으로 회원가입이 완료되셨습니다.")
                                                .build());
        return createAndDeleteUserResponse;
    }

    // 회원 정보 수정
    @Transactional
    public void updateUser(UpdateUserRequest updateUserRequest) {
       userRepository.update(updateUserRequest.getUserId(),
                             updateUserRequest.getUserPw(),
                             updateUserRequest.getUserContact(),
                             updateUserRequest.getUserAddress(),
                             updateUserRequest.getUserEmail());

       notificationRepository.save( Notification.builder()
                                                .userId(updateUserRequest.getUserId())
                                                .notificationTitle("회원 정보 수정 완료")
                                                .notificationContent("회원 정보가 성공적으로 수정되었습니다.")
                                                .build());
    }

    // 회원 탈퇴
    public CreateAndDeleteUserResponse deleteUser(String userId) {
        User willDeletedUser = userRepository.findByUserId(userId);
        userRepository.deleteById(willDeletedUser.getUserUuid());

        return willDeletedUser.toCreateAndDeleteUserResponse();
    }

}
