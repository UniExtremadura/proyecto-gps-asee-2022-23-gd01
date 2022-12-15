package es.unex.infinitetime.model;

import androidx.room.TypeConverter;

public class BooleanConverter {

    @TypeConverter
    public static boolean toBoolean(int value) {
        return value == 1;
    }
}
