package es.unex.infinitetime.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.model.UserShared;
import es.unex.infinitetime.repository.Repository;

public class SharedViewModel extends ViewModel {

    private final Repository repository;
    private long projectId;

    public SharedViewModel() {
        repository = Repository.getInstance();
    }

    public void setProjectId(long projectId) {
        repository.setProjectId(projectId);
    }

    public LiveData<List<UserShared>> getUsersShared() {
        return repository.getUsersShared();
    }

    public void switchShared(long userId, boolean share) {
        Log.d("SharedViewModel", "switchShared: " + userId + " " + share);
        if (share) {
            repository.shareProject(userId);
        }
        else {
            repository.stopSharingProject(userId);
        }
    }
}
