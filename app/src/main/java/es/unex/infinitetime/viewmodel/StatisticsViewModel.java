package es.unex.infinitetime.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.unex.infinitetime.model.TaskState;
import es.unex.infinitetime.repository.Repository;

public class StatisticsViewModel extends ViewModel {

    private final Repository repository;

    public StatisticsViewModel() {
        repository = Repository.getInstance();
    }

    public LiveData<Integer> getTasksNumToDo(){
        return repository.getTasksNumByState(TaskState.TODO.ordinal());
    }

    public LiveData<Integer> getTasksNumDoing(){
        return repository.getTasksNumByState(TaskState.DOING.ordinal());
    }

    public LiveData<Integer> getTasksNumDone(){
        return repository.getTasksNumByState(TaskState.DONE.ordinal());
    }
}
