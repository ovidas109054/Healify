package com.project.healify.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "messages")
public class MessageEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String senderEmail;
    public String receiverEmail;
    public String content;
    public long timestamp;

    public MessageEntity(String senderEmail, String receiverEmail, String content, long timestamp) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.content = content;
        this.timestamp = timestamp;
    }
}
