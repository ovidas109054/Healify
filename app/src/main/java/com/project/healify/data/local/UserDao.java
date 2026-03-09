package com.project.healify.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity user);

    @Query("SELECT * FROM users WHERE email = :email")
    UserEntity getUser(String email);

    @Query("SELECT * FROM users WHERE role = 'Doctor'")
    List<UserEntity> getAllDoctors();

    @Insert
    void insertHealthData(HealthDataEntity data);

    @Query("SELECT * FROM health_data WHERE userEmail = :email ORDER BY timestamp DESC")
    List<HealthDataEntity> getHealthDataForUser(String email);

    @Insert
    void insertMessage(MessageEntity message);

    @Query("SELECT * FROM messages WHERE (senderEmail = :user1 AND receiverEmail = :user2) OR (senderEmail = :user2 AND receiverEmail = :user1) ORDER BY timestamp ASC")
    List<MessageEntity> getMessagesBetween(String user1, String user2);
}
