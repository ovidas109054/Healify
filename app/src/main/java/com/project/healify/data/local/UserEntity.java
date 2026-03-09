package com.project.healify.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey
    @NonNull
    public String email;
    public String name;
    public String role;
    public int age;
    public double weight;
    public String bloodGroup;
    public String height;
    
    // Location details
    public String division;
    public String district;
    public String upazila;
    public String postalCode;

    public UserEntity(@NonNull String email) {
        this.email = email;
    }
}
