package com.project.healify.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class, HealthDataEntity.class, MessageEntity.class}, version = 2)
public abstract class HealifyDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
