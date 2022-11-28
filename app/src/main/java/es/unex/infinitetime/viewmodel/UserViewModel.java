package es.unex.infinitetime.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.repository.Repository;
import es.unex.infinitetime.ui.login.PersistenceUser;

public class UserViewModel extends ViewModel {
    private final Repository repository;
    private final long userId;

    public UserViewModel() {
        repository = Repository.getInstance(null);
        userId = PersistenceUser.getInstance().getUserId();
    }

    public LiveData<User> getUser() {
        return repository.getUserDAO().getUser(userId);
    }

    public long getUserId() {
        return userId;
    }

    public void updateUser(User user) {
        AppExecutors.getInstance().diskIO().execute(() -> repository.getUserDAO().update(user));
    }

    public void deleteUser(User user) {
        AppExecutors.getInstance().diskIO().execute(() -> repository.getUserDAO().delete(user));
    }

    public void insertUser(User user) {
        AppExecutors.getInstance().diskIO().execute(() -> repository.getUserDAO().insert(user));
    }
}
