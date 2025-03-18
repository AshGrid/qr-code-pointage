package com.ashgrid.qrcodeapp.repositories;

import com.ashgrid.qrcodeapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<User> findUserByPhoneId(String phoneId);

}
