package es.unex.infinitetime.repository;

import android.content.SharedPreferences;

public class PersistenceUser {

    private static PersistenceUser instance;
    private static final String KEY_USER_ID = "user_id";
    private static final long NO_USER = 1000000000;

    private long userId;
    private SharedPreferences preferences;

    public static PersistenceUser getInstance() {
        if(instance == null){
            instance = new PersistenceUser();
        }
        return instance;
    }

    private PersistenceUser() {
        userId = NO_USER;
    }

    public boolean isSessionOpen() {
        return userId != NO_USER;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void saveUserId(){
        SharedPreferences.Editor editor =  preferences.edit();
        editor.putLong(KEY_USER_ID, userId);
        editor.apply();
    }

    public void deleteUserId(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_USER_ID);
        editor.apply();
        setUserId(NO_USER);
    }

    public void loadUserId(){
        long value = preferences.getLong(KEY_USER_ID, NO_USER);
        setUserId(value);
    }
}
