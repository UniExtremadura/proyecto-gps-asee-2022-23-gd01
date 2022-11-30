package es.unex.infinitetime.api;

import java.util.List;
import java.util.stream.Collectors;

import es.unex.infinitetime.model.Favorite;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.model.Project;
import es.unex.infinitetime.model.ProjectDAO;
import es.unex.infinitetime.model.SharedProject;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskDAO;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.model.UserDAO;
import es.unex.infinitetime.ui.login.PersistenceUser;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadToAPI implements Runnable {

    public static final String BASE_URL = "https://sheet.best/api/sheets/421e4602-4291-4ed8-aed3-83969a14ea31/";

    private final UserRemoteDAO userRemoteDAO;
    private final ProjectRemoteDAO projectRemoteDAO;
    private final TaskRemoteDAO taskRemoteDAO;
    private final FavoriteRemoteDAO favoriteRemoteDAO;
    private final SharedProjectRemoteDAO sharedProjectRemoteDAO;

    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;

    public UploadToAPI(Retrofit retrofit) {

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
            List<User> users = userDAO.getAllUsers().getValue();
            userRemoteDAO.deleteUsers().execute();
            List<UserRemote> usersRemote = users.stream().map(
                    UserRemote::fromUser
            ).collect(Collectors.toList());
            userRemoteDAO.insertUsers(usersRemote).execute();

            List<Project> projects = projectDAO.getAllProjects();
            projectRemoteDAO.deleteProjects().execute();
            List<ProjectRemote> projectsRemote = projects.stream().map(
                    ProjectRemote::fromProject
            ).collect(Collectors.toList());
            projectRemoteDAO.insertProjects(projectsRemote).execute();

            List<Task> tasks = taskDAO.getAllTasks();
            taskRemoteDAO.deleteTasks().execute();
            List<TaskRemote> tasksRemote = tasks.stream().map(
                    TaskRemote::fromTask
            ).collect(Collectors.toList());
            taskRemoteDAO.insertTasks(tasksRemote).execute();

            List<Favorite> favorites = taskDAO.getAllFavorites();
            favoriteRemoteDAO.deleteFavorites().execute();
            List<FavoriteRemote> favoritesRemote = favorites.stream().map(
                    FavoriteRemote::fromFavorite
            ).collect(Collectors.toList());
            favoriteRemoteDAO.insertFavorites(favoritesRemote).execute();

            List<SharedProject> sharedProjects = projectDAO.getAllSharedProjects();
            sharedProjectRemoteDAO.deleteSharedProjects().execute();
            List<SharedProjectRemote> sharedProjectsRemote = sharedProjects.stream().map(
                    SharedProjectRemote::fromSharedProject
            ).collect(Collectors.toList());
            sharedProjectRemoteDAO.insertSharedProjects(sharedProjectsRemote).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
