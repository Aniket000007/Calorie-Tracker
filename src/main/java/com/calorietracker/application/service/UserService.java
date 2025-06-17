package com.calorietracker.application.service;

import com.calorietracker.application.dto.UpdateUserDTO;
import com.calorietracker.application.dto.UserDTO;
import com.calorietracker.application.model.User;

public interface UserService {
    User createUser(UserDTO userDTO) throws Exception;
    User updateUser(String id, UpdateUserDTO updateUserDTO) throws Exception;
    User getUser(String id) throws Exception;
}
