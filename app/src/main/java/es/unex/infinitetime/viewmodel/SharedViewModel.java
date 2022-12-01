package es.unex.infinitetime.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.repository.Repository;

public class SharedViewModel extends ViewModel {

    private final Repository repository;
    private long projectId;

    public SharedViewModel() {
        repository = Repository.getInstance();
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public LiveData<List<User>> getUsers() {
        return repository.getAllUsers();
    }

    public void switchShared(long userId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            boolean isShared = repository.isShared(userId, projectId);
            Log.d("Depurando", "isShared: " + userId + " " + projectId + " " + isShared);
            if (isShared) {
                repository.stopSharingProject(userId, projectId);
            } else {
                repository.shareProject(userId, projectId);
            }
        });
    }

    public boolean isShared(long userId) {
        return repository.isShared(userId, projectId);
    }
}
