package es.unex.infinitetime.repository;

import android.util.Log;

import java.util.List;
import java.util.stream.Collectors;

import es.unex.infinitetime.api.FavoriteRemote;
import es.unex.infinitetime.api.FavoriteRemoteDAO;
import es.unex.infinitetime.api.ProjectRemote;
import es.unex.infinitetime.api.ProjectRemoteDAO;
import es.unex.infinitetime.api.RemoteDAOs;
import es.unex.infinitetime.api.SharedProjectRemote;
import es.unex.infinitetime.api.SharedProjectRemoteDAO;
import es.unex.infinitetime.api.TaskRemote;
import es.unex.infinitetime.api.TaskRemoteDAO;
import es.unex.infinitetime.api.UserRemote;
import es.unex.infinitetime.api.UserRemoteDAO;
import es.unex.infinitetime.model.Favorite;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.model.Project;
import es.unex.infinitetime.model.ProjectDAO;
import es.unex.infinitetime.model.SharedProject;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskDAO;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.model.UserDAO;

public class UploadToAPI implements Runnable {

    private final UserRemoteDAO userRemoteDAO;
    private final ProjectRemoteDAO projectRemoteDAO;
    private final TaskRemoteDAO taskRemoteDAO;
    private final FavoriteRemoteDAO favoriteRemoteDAO;
    private final SharedProjectRemoteDAO sharedProjectRemoteDAO;

    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;

    public UploadToAPI(InfiniteDatabase database, RemoteDAOs remoteDAOs) {
        userDAO = database.userDAO();
        projectDAO = database.projectDAO();
        taskDAO = database.taskDAO();

        userRemoteDAO = remoteDAOs.getUserRemoteDAO();
        projectRemoteDAO = remoteDAOs.getProjectRemoteDAO();
        taskRemoteDAO = remoteDAOs.getTaskRemoteDAO();
        favoriteRemoteDAO = remoteDAOs.getFavoriteRemoteDAO();
        sharedProjectRemoteDAO = remoteDAOs.getSharedProjectRemoteDAO();
    }

    @Override
    public void run() {
        try {
            Log.d("UploadToAPI", "Starting upload to API");
            List<User> users = userDAO.getAllUsersWithoutLiveData();
            assert users != null;
            userRemoteDAO.deleteUsers().execute();
            List<UserRemote> usersRemote = users.stream().map(
                    UserRemote::fromUser
            ).collect(Collectors.toList());
            userRemoteDAO.insertUsers(usersRemote).execute();

            List<Project> projects = projectDAO.getAllProjects();
            projectRemoteDAO.deleteProjects().execute();
            assert projects != null;
            List<ProjectRemote> projectsRemote = projects.stream().map(
                    ProjectRemote::fromProject
            ).collect(Collectors.toList());
            projectRemoteDAO.insertProjects(projectsRemote).execute();

            List<Task> tasks = taskDAO.getAllTasks();
            taskRemoteDAO.deleteTasks().execute();
            assert tasks != null;
            List<TaskRemote> tasksRemote = tasks.stream().map(
                    TaskRemote::fromTask
            ).collect(Collectors.toList());
            taskRemoteDAO.insertTasks(tasksRemote).execute();

            List<Favorite> favorites = userDAO.getAllFavorites();
            favoriteRemoteDAO.deleteFavorites().execute();
            assert favorites != null;
            List<FavoriteRemote> favoritesRemote = favorites.stream().map(
                    FavoriteRemote::fromFavorite
            ).collect(Collectors.toList());
            favoriteRemoteDAO.insertFavorites(favoritesRemote).execute();

            List<SharedProject> sharedProjects = projectDAO.getAllSharedProjects();
            sharedProjectRemoteDAO.deleteSharedProjects().execute();
            assert sharedProjects != null;
            List<SharedProjectRemote> sharedProjectsRemote = sharedProjects.stream().map(
                    SharedProjectRemote::fromSharedProject
            ).collect(Collectors.toList());
            sharedProjectRemoteDAO.insertSharedProjects(sharedProjectsRemote).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
