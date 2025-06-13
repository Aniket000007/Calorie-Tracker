package com.calorietracker.application.dto;

import com.calorietracker.application.enums.GoalType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {
    public String username;
    public String name;
    public String email;
    public String mobileNumber;
    public LocalDate dateOfBirth;
    public Integer age;
    public Float weight;
    public Float height;
    public GoalType goalType;
    public Integer dailyGoal;

    // Getters and setters (or use Lombok if preferred)
}