package com.calorietracker.application.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

import com.calorietracker.application.dto.*;
import com.calorietracker.application.enums.*;

import com.calorietracker.application.model.User;
import com.calorietracker.application.service.UserService;



@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User CreateUser(@RequestBody UserDTO userDTO)  throws Exception{

        // check if the payload received is in userDTO form or not
        if (userDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserDTO is required");
        }

        // check if the payload received is in userDTO form or not
        if (userDTO.name == null || userDTO.email == null || userDTO.age == null || userDTO.weight == null || userDTO.height == null || userDTO.goalType == null || userDTO.dailyActivity == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All fields are required");
        }
        // check if the gender is correct
        if (userDTO.gender != Gender.MALE && userDTO.gender != Gender.FEMALE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gender must be MALE or FEMALE");
        }
        // check if the daily activity is correct
        if (userDTO.dailyActivity != DailyActivity.SEDENTARY && userDTO.dailyActivity != DailyActivity.MODERATELY_ACTIVE && userDTO.dailyActivity != DailyActivity.EXTREMELY_ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Daily activity must be SEDENTARY, MODERATELY_ACTIVE, or EXTREMELY_ACTIVE");
        }

       // check if name is corrext
       if (userDTO.name.length() < 3) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name must be at least 3 characters long");
       }
       // check if email is correct
       if (!userDTO.email.contains("@")) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email must contain @");
       }
       // check if username is correct
       if (userDTO.username.length() < 3) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must be at least 3 characters long");
       }
       // check if mobile number is correct
       if (userDTO.mobileNumber.length() != 10) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mobile number must be 10 digits long");
       }
       // check if date of birth is correct
       if (userDTO.dateOfBirth.isAfter(LocalDate.now())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date of birth cannot be in the future");
       }

        // check if the goal type is correct
        if (userDTO.goalType != GoalType.MAINTAIN && userDTO.goalType != GoalType.CUT && userDTO.goalType != GoalType.BULK) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Goal type must be MAINTAIN, CUT, or BULK");
        }
        
        return userService.createUser(userDTO);
    }

    @PutMapping("/update/{id}")
    public User UpdateUser(@PathVariable String id, @RequestBody UpdateUserDTO userDTO) throws Exception {

        if (userDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UpdateUserDTO is required");
        }
        return userService.updateUser(id, userDTO);
    }

    @GetMapping("/get/{id}")
    public User GetUser(@PathVariable String id) throws Exception {
        return userService.getUser(id);
    }
}
