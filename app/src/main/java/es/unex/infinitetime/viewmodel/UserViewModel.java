package es.unex.infinitetime.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.unex.infinitetime.model.User;
import es.unex.infinitetime.repository.Repository;

public class UserViewModel extends ViewModel {
    private final Repository repository;

    public UserViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<User> getUser() {
        return repository.getUser();
    }

    public boolean usernameExists(String username) {
        return repository.userExists(username);
    }

    public void updateUser(User user) {
        repository.updateUser(user);
    }

    public void deleteUser(long userId) {
        repository.deleteUser(userId);
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public long getUserId() {
        return repository.getUserId().getValue();
    }

    public User getUserByUsername(String username) {
        return repository.getUserByUsername(username);
    }

    public User getUserWithoutLiveData() {
        return repository.getUserWithoutLiveData();
    }

    public void closeSession() {
        repository.closeSession();
    }

    public void openSession(long userId) {
        repository.openSession(userId);
    }

    public boolean isSessionOpen() {
        return repository.isSessionOpen();
    }
}
