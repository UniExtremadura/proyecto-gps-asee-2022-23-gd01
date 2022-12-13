package es.unex.infinitetime.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.model.Favorite;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskState;
import es.unex.infinitetime.repository.Repository;

public class TaskViewModel extends ViewModel {

    private final Repository repository;
    private final MutableLiveData<Task> selectedTask = new MutableLiveData<>();

    private final MutableLiveData<Integer> selectedTaskState = new MutableLiveData<>();
    private final MutableLiveData<Long> selectedProject = new MutableLiveData<>();

    public TaskViewModel() {
        repository = Repository.getInstance();
    }

    public void setProjectId(long projectId) {
        selectedProject.setValue(projectId);
    }

    public long getProjectId() {
        return selectedProject.getValue();
    }

    public void setState(TaskState state) {
        selectedTaskState.setValue(state.ordinal());
    }

    public LiveData<List<Task>> getFavoriteTasks() {
        return repository.getFavoriteTasks();
    }

    public LiveData<List<Task>> getTasksProject() {
        return Transformations.switchMap(selectedProject, repository::getTasksProject);
    }

    public void selectTask(Task task) {
        selectedTask.setValue(task);
    }

    public LiveData<Task> getSelectedTask() {
        return selectedTask;
    }

    public boolean isInFavorite(long taskId) {
        return repository.isInFavorite(taskId);
    }

    public void switchFavorite(long taskId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            boolean isFavorite = repository.isInFavorite(taskId);
            if (isFavorite) {
                repository.removeFavorite(taskId);
            }
            else {
                repository.addFavorite(taskId);
            }
        });
    }

    public void insertTask(Task task) {
        repository.insertTask(task);
    }

    public void updateTask(Task task) {
        repository.updateTask(task);
    }

    public void deleteTask(long taskId) {
        repository.deleteTask(taskId);
    }
}

