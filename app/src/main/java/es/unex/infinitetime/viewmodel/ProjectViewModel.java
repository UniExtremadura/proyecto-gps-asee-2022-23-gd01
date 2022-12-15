package es.unex.infinitetime.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.infinitetime.model.Project;
import es.unex.infinitetime.repository.Repository;

public class ProjectViewModel extends ViewModel {

    private final Repository repository;
    private final MutableLiveData<Project> selectedProject;

    public ProjectViewModel(Repository repository) {
        this.repository = repository;
        selectedProject = new MutableLiveData<>();
    }

    public LiveData<List<Project>> getProjectsByUser() {
        return repository.getAllProjectsUser();
    }

    public void selectProject(Project project) {
        selectedProject.setValue(project);
    }

    public LiveData<Project> getSelectedProject() {
        return selectedProject;
    }

    public void insertProject(Project project) {
        repository.insertProject(project);
    }

    public void updateProject(Project project) {
        repository.updateProject(project);
    }

    public void deleteProject(long projectId) {
        repository.deleteProject(projectId);
    }
}
