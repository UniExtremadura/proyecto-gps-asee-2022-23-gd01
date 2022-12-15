package es.unex.infinitetime.model;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

public class TaskWithFavorite extends Task{

    @ColumnInfo(name = "is_favorite")
    @TypeConverters(BooleanConverter.class)
    private boolean isFavorite;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
