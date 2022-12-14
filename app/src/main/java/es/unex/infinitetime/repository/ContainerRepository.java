package es.unex.infinitetime.repository;

import es.unex.infinitetime.api.FavoriteRemoteDAO;
import es.unex.infinitetime.api.ProjectRemoteDAO;
import es.unex.infinitetime.api.SharedProjectRemoteDAO;
import es.unex.infinitetime.api.TaskRemoteDAO;
import es.unex.infinitetime.api.UserRemoteDAO;
import es.unex.infinitetime.model.InfiniteDatabase;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContainerRepository {

    public final String BASE_URL = "https://sheet.best/api/sheets/421e4602-4291-4ed8-aed3-83969a14ea31/";

    private final InfiniteDatabase database;

    private final UserRemoteDAO userRemoteDAO;
    private final ProjectRemoteDAO projectRemoteDAO;
    private final TaskRemoteDAO taskRemoteDAO;
    private final FavoriteRemoteDAO favoriteRemoteDAO;
    private final SharedProjectRemoteDAO sharedProjectRemoteDAO;

    private static ContainerRepository instance;


    public static ContainerRepository getInstance() {
        if (instance == null) {
            instance = new ContainerRepository();
        }
        return instance;
    }

    private ContainerRepository(){
        database = InfiniteDatabase.getDatabase(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userRemoteDAO = retrofit.create(UserRemoteDAO.class);
        projectRemoteDAO = retrofit.create(ProjectRemoteDAO.class);
        taskRemoteDAO = retrofit.create(TaskRemoteDAO.class);
        favoriteRemoteDAO = retrofit.create(FavoriteRemoteDAO.class);
        sharedProjectRemoteDAO = retrofit.create(SharedProjectRemoteDAO.class);
    }

    public InfiniteDatabase getDatabase(){
        return database;
    }

    public UserRemoteDAO getUserRemoteDAO(){
        return userRemoteDAO;
    }

    public ProjectRemoteDAO getProjectRemoteDAO(){
        return projectRemoteDAO;
    }

    public TaskRemoteDAO getTaskRemoteDAO(){
        return taskRemoteDAO;
    }

    public FavoriteRemoteDAO getFavoriteRemoteDAO(){
        return favoriteRemoteDAO;
    }

    public SharedProjectRemoteDAO getSharedProjectRemoteDAO(){
        return sharedProjectRemoteDAO;
    }
}
