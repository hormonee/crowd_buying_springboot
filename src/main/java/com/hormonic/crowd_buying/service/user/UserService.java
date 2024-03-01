package com.hormonic.crowd_buying.service.user;

import com.hormonic.crowd_buying.domain.entity.User;
import com.hormonic.crowd_buying.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User getUserByEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public List<User> getUserListByComponent(String component) {
        return userRepository.findAllByUserNameLike("%" + component + "%");
    }

    public User testCustomizedMethod(String element) {
        return userRepository.findByCustomizedMethod(element);
    }

    public List<User> testCustomizedMethod2(String element) {
        return userRepository.findByCustomizedMethod2("%" + element + "%");
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

}
