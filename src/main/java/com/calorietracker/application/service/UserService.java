package com.calorietracker.application.service;

import com.calorietracker.application.dto.UserDTO;
import com.calorietracker.application.model.User;

public interface UserService {
    User createUser(UserDTO userDTO);
    User getUser(String id);
}
