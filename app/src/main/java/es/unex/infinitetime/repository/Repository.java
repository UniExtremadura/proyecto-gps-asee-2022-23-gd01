package es.unex.infinitetime.repository;

import android.content.Context;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.model.ProjectDAO;
import es.unex.infinitetime.model.TaskDAO;
import es.unex.infinitetime.model.UserDAO;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    public static final String BASE_URL = "https://sheet.best/api/sheets/421e4602-4291-4ed8-aed3-83969a14ea31/";
    private static Repository instance;

    private final DownloadFromAPI downloadFromAPI;
    private final UploadToAPI uploadToAPI;

    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;

    private Repository(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        downloadFromAPI = new DownloadFromAPI(retrofit);
        uploadToAPI = new UploadToAPI(retrofit);

        userDAO = InfiniteDatabase.getDatabase(context).userDAO();
        projectDAO = InfiniteDatabase.getDatabase(context).projectDAO();
        taskDAO = InfiniteDatabase.getDatabase(context).taskDAO();
    }

    public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public ProjectDAO getProjectDAO() {
        return projectDAO;
    }

    public TaskDAO getTaskDAO() {
        return taskDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void initiate() {
        AppExecutors.getInstance().networkIO().execute(downloadFromAPI);
    }

    public void finish() {
        AppExecutors.getInstance().networkIO().execute(uploadToAPI);
    }
}
