package es.unex.infinitetime.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.infinitetime.repository.Repository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> viewModelClass){
        if (viewModelClass.isAssignableFrom(ProjectViewModel.class)) {
            return (T) new ProjectViewModel(repository);
        } else if(viewModelClass.isAssignableFrom(SharedViewModel.class)) {
            return (T) new SharedViewModel(repository);
        } else if(viewModelClass.isAssignableFrom(StatisticsViewModel.class)) {
            return (T) new StatisticsViewModel(repository);
        } else if(viewModelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(repository);
        } else if(viewModelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(repository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
