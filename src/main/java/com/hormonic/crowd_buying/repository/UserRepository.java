package com.hormonic.crowd_buying.repository;

import com.hormonic.crowd_buying.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
//    List<User> findAll(Specification<User> spec, Pageable pageable);

//    User findByUserName(String userName);

    User findByUserEmail(String userEmail);

    List<User> findAllByUserNameLike(String component);
//    List<User> findAllByUserNameLike(String component, Pageable pageable);

    @Query("select u from User u where u.userName = :element")
    User findByCustomizedMethod(@Param("element") String element);

    @Query("select u from User u where u.userEmail like :element")
    List<User> findByCustomizedMethod2(@Param("element") String element);

}
