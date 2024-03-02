package com.hormonic.crowd_buying.service.user;

import com.hormonic.crowd_buying.domain.dto.request.SaveUserRequest;
import com.hormonic.crowd_buying.domain.dto.response.SaveUserResponse;
import com.hormonic.crowd_buying.domain.entity.User;
import com.hormonic.crowd_buying.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

//    public User getUserByName(String userName) {
//        return userRepository.findByUserName(userName);
//    }

    public User getUserByEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

//    public List<User> getUserList() {
//        return userRepository.findAll();
//    }

    public List<User> getUserListByComponent(String component) {
        return userRepository.findAllByUserNameLike("%" + component + "%");
    }

    public User testCustomizedMethod(String element) {
        return userRepository.findByCustomizedMethod(element);
    }

    public List<User> testCustomizedMethod2(String element) {
        return userRepository.findByCustomizedMethod2("%" + element + "%");
    }

//    public User createUser(User user) {
//        return userRepository.save(user);
//    }

    @Transactional
    public SaveUserResponse saveUser(SaveUserRequest saveUserRequest) {
        User newUser = new User(saveUserRequest.getUserName(), saveUserRequest.getUserEmail());

        return userRepository.save(newUser).toSaveUserResponse();
    }

    /*
      MyBatis 메서드
     */
    public ArrayList<User> getUserList() {
        System.out.println("Service 진입");
        ArrayList<User> userList = userMapper.getUserList();
        System.out.println(userList);
        return userList;
    }

    public User getUserByName(String name) {
        System.out.println("Service 진입");
        User user = userMapper.getUserByName(name);
        System.out.println(user);
        return user;
    }

}
