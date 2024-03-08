package com.hormonic.crowd_buying.repository;

import com.hormonic.crowd_buying.domain.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Administrator, UUID> {

}
