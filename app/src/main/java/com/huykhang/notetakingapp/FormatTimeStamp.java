package com.huykhang.notetakingapp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import com.google.firebase.Timestamp;
public class FormatTimeStamp {
    public static String getCurrentTimestampAsString() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        return currentDateTime.format(formatter);
    }
}
