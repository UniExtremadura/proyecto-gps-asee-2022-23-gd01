package es.unex.infinitetime.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDAOs {

    public final String BASE_URL = "https://sheet.best/api/sheets/421e4602-4291-4ed8-aed3-83969a14ea31/";

    private final UserRemoteDAO userRemoteDAO;
    private final ProjectRemoteDAO projectRemoteDAO;
    private final TaskRemoteDAO taskRemoteDAO;
    private final FavoriteRemoteDAO favoriteRemoteDAO;
    private final SharedProjectRemoteDAO sharedProjectRemoteDAO;


    public RemoteDAOs(){
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

    public UserRemoteDAO getUserRemoteDAO() {
        return userRemoteDAO;
    }

    public ProjectRemoteDAO getProjectRemoteDAO() {
        return projectRemoteDAO;
    }

    public TaskRemoteDAO getTaskRemoteDAO() {
        return taskRemoteDAO;
    }

    public FavoriteRemoteDAO getFavoriteRemoteDAO() {
        return favoriteRemoteDAO;
    }

    public SharedProjectRemoteDAO getSharedProjectRemoteDAO() {
        return sharedProjectRemoteDAO;
    }

}
