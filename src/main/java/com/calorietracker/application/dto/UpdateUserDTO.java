package com.calorietracker.application.dto;

import com.calorietracker.application.enums.GoalType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateUserDTO {
    public String name;
    public String email;
    public String mobileNumber;
    public LocalDate dateOfBirth;
    public Float weight;
    public Float height;
    public GoalType goalType;
    public Integer dailyGoal;
} 