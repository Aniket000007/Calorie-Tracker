package com.calorietracker.application.repository;

import com.calorietracker.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.userUID = :userUID")
    User findByUserUID(@Param("userUID") String userUID);
}
