package es.unex.infinitetime.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskState;
import es.unex.infinitetime.repository.Repository;
import es.unex.infinitetime.ui.login.PersistenceUser;

public class TaskViewModel extends ViewModel {

    private final Repository repository;
    private final long userId;
    private long projectId;
    private final MutableLiveData<Task> selectedTask = new MutableLiveData<>();

    public TaskViewModel() {
        repository = Repository.getInstance(null);
        userId = PersistenceUser.getInstance().getUserId();
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getProjectId() {
        return projectId;
    }

    public LiveData<List<Task>> getFavoriteTasks() {
        return repository.getUserDAO().getFavoriteTasks(userId);
    }

    public LiveData<List<Task>> getTasksByState(TaskState state) {
        return repository.getTaskDAO().getTasksByState(projectId, state.ordinal());
    }

    public void selectTask(Task task) {
        selectedTask.setValue(task);
    }

    public LiveData<Task> getSelectedTask() {
        return selectedTask;
    }

    public void switchFavorite(long taskId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            boolean isFavorite = repository.getUserDAO().getFavorite(userId, taskId) != null;
            if (isFavorite) {
                repository.getTaskDAO().removeFavorite(userId, taskId);
            }
            else {
                repository.getTaskDAO().addFavorite(userId, taskId);
            }
        });
    }

    public void insertTask(Task task) {
        AppExecutors.getInstance().diskIO().execute(() -> repository.getTaskDAO().insert(task));
    }

    public void updateTask(Task task) {
        AppExecutors.getInstance().diskIO().execute(() -> repository.getTaskDAO().update(task));
    }

    public void deleteTask(Task task) {
        AppExecutors.getInstance().diskIO().execute(() -> repository.getTaskDAO().delete(task));
    }
}
