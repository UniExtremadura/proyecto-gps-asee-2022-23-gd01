package es.unex.infinitetime.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.repository.Repository;

public class SharedViewModel extends ViewModel {

    private final Repository repository;
    private long projectId;

    public SharedViewModel() {
        repository = Repository.getInstance(null);
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getProjectId() {
        return projectId;
    }

    public LiveData<List<User>> getUsers() {
        return repository.getUserDAO().getAllUsers();
    }

    public void switchShared(long userId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            boolean isShared = repository.getProjectDAO().getSharedProject(projectId, userId) != null;
            if (isShared) {
                repository.getProjectDAO().shareProject(projectId, userId);
            } else {
                repository.getProjectDAO().stopSharingProject(projectId, userId);
            }
        });
    }
}
