package com.calorietracker.application.serviceImpl;

import com.calorietracker.application.dto.*;
import com.calorietracker.application.enums.*;
import com.calorietracker.application.model.*;
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
            user.setGender(userDTO.getGender());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setMobileNumber(userDTO.getMobileNumber());
            user.setDateOfBirth(userDTO.getDateOfBirth());
            user.setAge(age);
            user.setWeight(userDTO.getWeight());
            user.setHeight(userDTO.getHeight());
            user.setGoalType(userDTO.getGoalType());
            user.setDailyGoal(calculateDailyCalorieGoal(userDTO.getHeight(), userDTO.getWeight(), userDTO.getGoalType(), userDTO.getDailyActivity(), userDTO.getGender(), age));
            User newUser = userRepository.save(user);
            return newUser;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating user: " + e.getMessage());
        }
    }

    public int calculateDailyCalorieGoal(float height, float weight, GoalType goalType, DailyActivity dailyActivity, Gender gender, int age){
        // calculate Basal Metabolic Rate (BMR)
        double bmr = 0;
        if (gender == Gender.MALE) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
        // Determine your Activity Level and calculate Total Daily Energy Expenditure (TDEE):
        double tdee = 0;
        if (dailyActivity == DailyActivity.SEDENTARY) {
            tdee = bmr * 1.2;
        } else if (dailyActivity == DailyActivity.MODERATELY_ACTIVE) {
            tdee = bmr * 1.55;
        } else if (dailyActivity == DailyActivity.EXTREMELY_ACTIVE) {
            tdee = bmr * 1.9;
        }
        // Adjust for your goal:
        double dailyGoal = 0;
        if (goalType == GoalType.CUT) {
            dailyGoal = tdee - 500;
        } else if (goalType == GoalType.MAINTAIN) {
            dailyGoal = tdee;
        } else if (goalType == GoalType.BULK) {
            dailyGoal = tdee + 500;
        }
        return (int) dailyGoal;
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
            // if (updateUserDTO.getEmail() != null) {
            //     if (!updateUserDTO.getEmail().contains("@")) {
            //         throw new IllegalArgumentException("Email must contain @");
            //     }else{
            //         user.setEmail(updateUserDTO.getEmail());
            //     }
            // }
            // if (updateUserDTO.getMobileNumber() != null) {
            //     if (updateUserDTO.getMobileNumber().length() != 10) {
            //         throw new IllegalArgumentException("Mobile number must be 10 digits long");
            //     }else{
            //         user.setMobileNumber(updateUserDTO.getMobileNumber());
            //     }
            // }
            // if (updateUserDTO.getDateOfBirth() != null) {
            //     if (updateUserDTO.getDateOfBirth().isAfter(LocalDate.now())) {
            //         throw new IllegalArgumentException("Date of birth cannot be in the future");
            //     }else{
            //         user.setDateOfBirth(updateUserDTO.getDateOfBirth());
            //     }
            // }
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