package com.hormonic.crowd_buying.service.user;
import com.hormonic.crowd_buying.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface UserMapper {
    public ArrayList<User> getUserList();

    public User getUserByName(String name);
}
