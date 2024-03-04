package com.hormonic.crowd_buying.service.user;

import com.hormonic.crowd_buying.domain.dto.request.CreateUserRequest;
import com.hormonic.crowd_buying.domain.dto.request.UpdateUserRequest;
import com.hormonic.crowd_buying.domain.dto.response.CreateAndDeleteUserResponse;
import com.hormonic.crowd_buying.domain.dto.response.UpdateUserResponse;
import com.hormonic.crowd_buying.domain.entity.User;
import com.hormonic.crowd_buying.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

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

        return userRepository.save(newUser).toCreateAndDeleteUserResponse();
    }

    @Transactional
    public int updateUser(UpdateUserRequest updateUserRequest) {
       return  userRepository.update(updateUserRequest.getUserId(), updateUserRequest.getUserPw(), updateUserRequest.getUserContact(),
                updateUserRequest.getUserAddress(), updateUserRequest.getUserEmail());
    }

    public CreateAndDeleteUserResponse deleteUser(String userId) {
        User willDeletedUser = userRepository.findByUserId(userId);
        userRepository.deleteById(willDeletedUser.getUserUuid());

        return willDeletedUser.toCreateAndDeleteUserResponse();
    }

    /*
      MyBatis Methods
     */

}
