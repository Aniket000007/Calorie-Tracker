package com.calorietracker.application.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

import com.calorietracker.application.dto.UserDTO;
import com.calorietracker.application.enums.GoalType;
import com.calorietracker.application.model.User;
import com.calorietracker.application.service.UserService;



@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User CreateUser(@RequestBody UserDTO userDTO) {

        // check if the payload received is in userDTO form or not
        if (userDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserDTO is required");
        }

        // check if the payload received is in userDTO form or not
        if (userDTO.name == null || userDTO.email == null || userDTO.age == null || userDTO.weight == null || userDTO.height == null || userDTO.goalType == null || userDTO.dailyGoal == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All fields are required");
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

    @GetMapping("/get/{id}")
    public User GetUser(@PathVariable String id) {
        return userService.getUser(id);
    }
}
