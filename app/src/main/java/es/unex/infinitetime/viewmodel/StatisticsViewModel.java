package es.unex.infinitetime.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.unex.infinitetime.repository.Repository;

public class StatisticsViewModel extends ViewModel {

    private final Repository repository;

    public StatisticsViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Integer> getTasksNumToDo(){
        return repository.getTasksNumToDo();
    }

    public LiveData<Integer> getTasksNumDoing(){
        return repository.getTasksNumDoing();
    }

    public LiveData<Integer> getTasksNumDone(){
        return repository.getTasksNumDone();
    }
}
