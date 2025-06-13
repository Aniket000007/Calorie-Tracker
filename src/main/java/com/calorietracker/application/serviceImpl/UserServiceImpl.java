package com.calorietracker.application.serviceImpl;

import com.calorietracker.application.dto.UserDTO;
import com.calorietracker.application.model.User;
import com.calorietracker.application.repository.UserRepository;
import com.calorietracker.application.service.UserService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserDTO userDTO) {

        try {
            int age = LocalDate.now().getYear() - userDTO.getDateOfBirth().getYear();
            if (age < 10) {
                throw new IllegalArgumentException("Age must be at least 10");
            }
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setMobileNumber(userDTO.getMobileNumber());
            user.setDateOfBirth(userDTO.getDateOfBirth());
            user.setAge(age);
            user.setWeight(userDTO.getWeight());
            user.setHeight(userDTO.getHeight());
            user.setGoalType(userDTO.getGoalType());
            user.setDailyGoal(userDTO.getDailyGoal());
            User newUser = userRepository.save(user);
            return newUser;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating user: " + e.getMessage());
        }
    }

    @Override
    public User getUser(String id) throws Exception{

        User user = userRepository.findByUserUID(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        return user;
    }
} 