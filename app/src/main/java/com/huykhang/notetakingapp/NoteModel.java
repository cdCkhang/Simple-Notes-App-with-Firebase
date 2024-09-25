package com.huykhang.notetakingapp;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

public class NoteModel {
    private String title;
    private String content;

    private String timestamp;

    public NoteModel() {
    }

    public  NoteModel (String title, String content, String timestamp){
        this.title=title;
        this.content=content;
        this.timestamp=timestamp;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(){
        this.timestamp = FormatTimeStamp.getCurrentTimestampAsString();
    }
}
