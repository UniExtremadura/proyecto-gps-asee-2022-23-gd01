package es.unex.infinitetime.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskWithFavorite;
import es.unex.infinitetime.repository.Repository;

public class TaskViewModel extends ViewModel {

    private final Repository repository;
    private final MutableLiveData<Task> selectedTask = new MutableLiveData<>();

    public TaskViewModel(Repository repository) {
        this.repository = repository;
    }

    public void setProjectId(long projectId) {
        repository.setTaskProjectId(projectId);
    }

    public long getProjectId() {
        return repository.getTaskProjectId().getValue();
    }

    public LiveData<List<Task>> getFavoriteTasks() {
        return repository.getFavoriteTasks();
    }

    public LiveData<List<TaskWithFavorite>> getTasksToDo() {
        return repository.getTasksToDo();
    }

    public LiveData<List<TaskWithFavorite>> getTasksDoing() {
        return repository.getTasksDoing();
    }

    public LiveData<List<TaskWithFavorite>> getTasksDone() {
        return repository.getTasksDone();
    }

    public void selectTask(Task task) {
        selectedTask.setValue(task);
    }

    public LiveData<Task> getSelectedTask() {
        return selectedTask;
    }

    public void switchFavorite(long taskId, boolean favorite) {
        if (favorite) {
            repository.addFavorite(taskId);
        }
        else {
            repository.removeFavorite(taskId);
        }
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

