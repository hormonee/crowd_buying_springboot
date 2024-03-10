package com.hormonic.crowd_buying.service.user;
import com.hormonic.crowd_buying.domain.entity.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface UserMapper {
    public ArrayList<Users> getUserList();

    public Users getUserByName(String name);
}
