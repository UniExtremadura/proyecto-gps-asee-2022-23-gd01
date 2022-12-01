package es.unex.infinitetime.repository;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PersistenceUser {

    private static PersistenceUser instance;
    private static final String KEY_USER_ID = "user_id";
    private static final long NO_USER = 1000000000;

    private final MutableLiveData<Long> userId;
    private SharedPreferences preferences;

    public static PersistenceUser getInstance() {
        if(instance == null){
            instance = new PersistenceUser();
            instance.setUserId(NO_USER);
        }
        return instance;
    }

    private PersistenceUser() {
        userId = new MutableLiveData<>();
    }

    public LiveData<Long> getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId.setValue(userId);
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void saveUserId(){
        SharedPreferences.Editor editor =  preferences.edit();
        if(userId.getValue() != null){
            editor.putLong(KEY_USER_ID, userId.getValue());
        } else {
            editor.putLong(KEY_USER_ID, NO_USER);
        }
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

    public boolean hasValidUserId(){
        return userId.getValue() != NO_USER;
    }
}
