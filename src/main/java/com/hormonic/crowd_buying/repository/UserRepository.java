package com.hormonic.crowd_buying.repository;

import com.hormonic.crowd_buying.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    Users findByUserId(String userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Users u set u.userPw = :userPw, u.userContact = :userContact, " +
            "u.userAddress = :userAddress, u.userEmail = :userEmail where u.userId = :userId")
    int update(@Param("userId") String userId,
               @Param("userPw") String userPw,
               @Param("userContact") String userContact,
               @Param("userAddress") String userAddress,
               @Param("userEmail") String userEmail);

//    List<User> findAll(Specification<User> spec, Pageable pageable);

//    @Query("select u from User u where u.userName = :element")
//    User findByCustomizedMethod(@Param("element") String element);
}
