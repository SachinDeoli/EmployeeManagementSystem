package com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.repository;

import com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
//    User findByEmail(String email);
//    User findByUsernameOrEmail(String username, String email);
//    List<User> findByIdIn(List<Long> userIds);
//    Boolean existsByUsername(String username);
//    Boolean existsByEmail(String email);
}
