package com.calorietracker.application.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.calorietracker.application.enums.GoalType;
import com.calorietracker.application.util.IdGenerator;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uid", unique = true, nullable = false)
    private String userUID;

    @Column(unique = true, nullable = false)
    private String username;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private Integer age;

    private Float weight;

    private Float height;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal_type")
    private GoalType goalType = GoalType.MAINTAIN;

    @Column(name = "daily_goal")
    private Integer dailyGoal;

    @Column(name = "created_at")
    private long createdAt;

    @Column(name = "updated_at")
    private long updatedAt;

    @PrePersist
    protected void onCreate() {
        userUID = IdGenerator.generateUserId();
        createdAt = System.currentTimeMillis();
        updatedAt = System.currentTimeMillis();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = System.currentTimeMillis();
    }

    // Getters and setters
    // Constructors
}
