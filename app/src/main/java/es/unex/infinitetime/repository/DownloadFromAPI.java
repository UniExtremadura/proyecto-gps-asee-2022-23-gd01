package es.unex.infinitetime.repository;

import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import es.unex.infinitetime.api.FavoriteRemote;
import es.unex.infinitetime.api.FavoriteRemoteDAO;
import es.unex.infinitetime.api.ProjectRemote;
import es.unex.infinitetime.api.ProjectRemoteDAO;
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
import retrofit2.Retrofit;

public class DownloadFromAPI implements Runnable{

    private final UserRemoteDAO userRemoteDAO;
    private final ProjectRemoteDAO projectRemoteDAO;
    private final TaskRemoteDAO taskRemoteDAO;
    private final FavoriteRemoteDAO favoriteRemoteDAO;
    private final SharedProjectRemoteDAO sharedProjectRemoteDAO;

    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;

    public DownloadFromAPI(Retrofit retrofit) {

        userRemoteDAO = retrofit.create(UserRemoteDAO.class);
        projectRemoteDAO = retrofit.create(ProjectRemoteDAO.class);
        taskRemoteDAO = retrofit.create(TaskRemoteDAO.class);
        favoriteRemoteDAO = retrofit.create(FavoriteRemoteDAO.class);
        sharedProjectRemoteDAO = retrofit.create(SharedProjectRemoteDAO.class);

        userDAO = InfiniteDatabase.getDatabase(null).userDAO();
        projectDAO = InfiniteDatabase.getDatabase(null).projectDAO();
        taskDAO = InfiniteDatabase.getDatabase(null).taskDAO();
    }

    @Override
    public void run() {
        try {
            List<UserRemote> usersRemote = userRemoteDAO.getUsers().execute().body();
            List<User> users = usersRemote.stream().map(
                    User::userFromRemote
            ).collect(Collectors.toList());

            userDAO.deleteAllUsers();
            users.forEach(userDAO::insert);

            List<ProjectRemote> projectsRemote = projectRemoteDAO.getProjects().execute().body();
            List<Project> projects = projectsRemote.stream().map(
                    Project::projectFromRemote
            ).collect(Collectors.toList());

            projectDAO.deleteAllProjects();
            projects.forEach(projectDAO::insert);

            List<TaskRemote> tasksRemote = taskRemoteDAO.getTasks().execute().body();
            List<Task> tasks = tasksRemote.stream().map(
                    Task::fromRemote
            ).collect(Collectors.toList());

            taskDAO.deleteAllTasks();
            tasks.forEach(taskDAO::insert);

            List<FavoriteRemote> favoritesRemote = favoriteRemoteDAO.getFavorites().execute().body();
            List<Favorite> favorites = favoritesRemote.stream().map(
                    Favorite::fromRemote
            ).collect(Collectors.toList());

            userDAO.deleteAllFavorites();
            favorites.forEach(userDAO::insertFavorite);

            List<SharedProjectRemote> sharedProjectsRemote = sharedProjectRemoteDAO.getSharedProjects().execute().body();
            List<SharedProject> sharedProjects = sharedProjectsRemote.stream().map(
                    SharedProject::fromRemote
            ).collect(Collectors.toList());

            userDAO.deleteAllSharedProjects();
            sharedProjects.forEach(userDAO::insertSharedProject);

            Log.d("DownloadFromAPI", "Downloaded from API");
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
