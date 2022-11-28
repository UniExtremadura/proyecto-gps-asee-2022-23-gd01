package es.unex.infinitetime.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.model.Project;
import es.unex.infinitetime.repository.Repository;
import es.unex.infinitetime.ui.login.PersistenceUser;

public class ProjectViewModel extends ViewModel {

    private final Repository repository;
    private final long userId;
    private final MutableLiveData<Project> selectedProject = new MutableLiveData<>();

    public ProjectViewModel() {
        repository = Repository.getInstance(null);
        userId = PersistenceUser.getInstance().getUserId();
    }

    public LiveData<List<Project>> getProjectsByUser() {
        return repository.getUserDAO().getAllProjectsOfUser(userId);
    }

    public void selectProject(Project project) {
        selectedProject.setValue(project);
    }

    public LiveData<Project> getSelectedProject() {
        return selectedProject;
    }

    public void insertProject(Project project) {
        AppExecutors.getInstance().diskIO().execute(() -> repository.getProjectDAO().insert(project));
    }

    public void updateProject(Project project) {
        AppExecutors.getInstance().diskIO().execute(() -> repository.getProjectDAO().update(project));
    }

    public void deleteProject(Project project) {
        AppExecutors.getInstance().diskIO().execute(() -> repository.getProjectDAO().delete(project));
    }
}
