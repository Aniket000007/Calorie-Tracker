package com.calorietracker.application.serviceImpl;

import com.calorietracker.application.dto.UpdateUserDTO;
import com.calorietracker.application.dto.UserDTO;
import com.calorietracker.application.enums.GoalType;
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
    public User updateUser(String id, UpdateUserDTO updateUserDTO) throws Exception{
        try {
            User user = userRepository.findByUserUID(id);
            if (user == null) {
                throw new IllegalArgumentException("User not found with id: " + id);
            }

            if (updateUserDTO.getUsername() != null) {
                if (updateUserDTO.getUsername().length() < 3) {
                    throw new IllegalArgumentException("Username must be at least 3 characters long");
                }else{
                    user.setUsername(updateUserDTO.getUsername());
                }
            }

            if (updateUserDTO.getName() != null) {
                if (updateUserDTO.getName().length() < 3) {
                    throw new IllegalArgumentException("Name must be at least 3 characters long");
                }else{
                    user.setName(updateUserDTO.getName());
                }
            }
            if (updateUserDTO.getEmail() != null) {
                if (!updateUserDTO.getEmail().contains("@")) {
                    throw new IllegalArgumentException("Email must contain @");
                }else{
                    user.setEmail(updateUserDTO.getEmail());
                }
            }
            if (updateUserDTO.getMobileNumber() != null) {
                if (updateUserDTO.getMobileNumber().length() != 10) {
                    throw new IllegalArgumentException("Mobile number must be 10 digits long");
                }else{
                    user.setMobileNumber(updateUserDTO.getMobileNumber());
                }
            }
            if (updateUserDTO.getDateOfBirth() != null) {
                if (updateUserDTO.getDateOfBirth().isAfter(LocalDate.now())) {
                    throw new IllegalArgumentException("Date of birth cannot be in the future");
                }else{
                    user.setDateOfBirth(updateUserDTO.getDateOfBirth());
                }
            }
            if (updateUserDTO.getWeight() != null) {
                if (updateUserDTO.getWeight() <= 0) {
                    throw new IllegalArgumentException("Weight must be greater than 0");
                }else{
                    user.setWeight(updateUserDTO.getWeight());
                }
            }
            if (updateUserDTO.getHeight() != null) {
                if (updateUserDTO.getHeight() <= 0) {
                    throw new IllegalArgumentException("Height must be greater than 0");
                }else{
                    user.setHeight(updateUserDTO.getHeight());
                }
            }
            if (updateUserDTO.getGoalType() != null) {
                if (updateUserDTO.getGoalType() != GoalType.CUT && updateUserDTO.getGoalType() != GoalType.MAINTAIN && updateUserDTO.getGoalType() != GoalType.BULK) {
                    throw new IllegalArgumentException("Invalid goal type");
                }else{
                    user.setGoalType(updateUserDTO.getGoalType());
                }
            }
            if (updateUserDTO.getDailyGoal() != null) {
                if (updateUserDTO.getDailyGoal() <= 0) {
                    throw new IllegalArgumentException("Daily goal must be greater than 0");
                }else{
                    user.setDailyGoal(updateUserDTO.getDailyGoal());
                }
            }
            User newUser = userRepository.save(user);
            return newUser;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating user: " + e.getMessage());
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