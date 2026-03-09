package com.project.healify.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "health_data")
public class HealthDataEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String userEmail;
    public double weight;
    public int heartRate;
    public long timestamp;

    public HealthDataEntity(String userEmail, double weight, int heartRate, long timestamp) {
        this.userEmail = userEmail;
        this.weight = weight;
        this.heartRate = heartRate;
        this.timestamp = timestamp;
    }
}
