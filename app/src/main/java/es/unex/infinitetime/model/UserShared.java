package es.unex.infinitetime.model;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

public class UserShared {

    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "is_shared")
    @TypeConverters(BooleanConverter.class)
    private boolean isShared;

    public UserShared(long id, String username, boolean isShared) {
        this.id = id;
        this.username = username;
        this.isShared = isShared;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }
}
