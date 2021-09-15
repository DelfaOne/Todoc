package com.example.todoc.data.converters;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;

public class Converters {

    @TypeConverter
    public static LocalDateTime toDate(String value) {
        if (value == null) {
            return null;
        } else {
            return LocalDateTime.parse(value);
        }
    }

    @TypeConverter
    public static String toDateString(LocalDateTime date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
}
