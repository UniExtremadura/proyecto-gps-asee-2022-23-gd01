package es.unex.infinitetime.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.api.DownloadFromAPI;
import es.unex.infinitetime.api.UploadToAPI;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.model.Project;
import es.unex.infinitetime.model.ProjectDAO;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskDAO;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.model.UserDAO;
import es.unex.infinitetime.ui.login.PersistenceUser;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private static Repository instance;

    public final String BASE_URL = "https://sheet.best/api/sheets/421e4602-4291-4ed8-aed3-83969a14ea31/";

    private final UploadToAPI uploadToAPI;
    private final DownloadFromAPI downloadFromAPI;

    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;

    private long projectId;
    private long taskId;

    private Repository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userDAO = InfiniteDatabase.getDatabase(null).userDAO();
        projectDAO = InfiniteDatabase.getDatabase(null).projectDAO();
        taskDAO = InfiniteDatabase.getDatabase(null).taskDAO();

        downloadFromAPI = new DownloadFromAPI(retrofit);
        uploadToAPI = new UploadToAPI(retrofit);

        projectId = 0;
        taskId = 0;
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void uploadToAPI() {
        AppExecutors.getInstance().networkIO().execute(uploadToAPI);
    }

    public void downloadFromAPI() {
        AppExecutors.getInstance().networkIO().execute(downloadFromAPI);
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getUserId() {
        return PersistenceUser.getInstance().getUserId();
    }

    public LiveData<User> getUser(){
        return userDAO.getUser(getUserId());
    }

    public LiveData<Project> getProject(){
        return projectDAO.getProject(projectId);
    }

    public LiveData<List<Project>> getAllProjectsUser(){
        return userDAO.getAllProjectsUser(getUserId());
    }

    public LiveData<Task> getTask(){
        return taskDAO.getTask(taskId);
    }

    public LiveData<List<Task>> getAllTasksProject(){
        return projectDAO.getTasks(projectId);
    }

    public boolean userExists(String username){
        return userDAO.getUser(username) != null;
    }

    public LiveData<List<User>> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public LiveData<Integer> getTasksNumByState(int state){
        return taskDAO.getTasksNum(getUserId(), state);
    }

    public boolean isInFavorite(long taskId){
        return taskDAO.getFavorite(getUserId(), taskId) != null;
    }

    public boolean isShared(long projectId){
        return projectDAO.getSharedProject(getUserId(), projectId) != null;
    }

    public void insertUser(User user){
        AppExecutors.getInstance().diskIO().execute(() -> userDAO.insert(user));
    }

    public void updateUser(User user){
        AppExecutors.getInstance().diskIO().execute(() -> userDAO.update(user));
    }

    public void deleteUser(User user){
        AppExecutors.getInstance().diskIO().execute(() -> userDAO.delete(user));
    }

    public void insertTask(Task task){
        AppExecutors.getInstance().diskIO().execute(() -> taskDAO.insert(task));
    }

    public void updateTask(Task task){
        AppExecutors.getInstance().diskIO().execute(() -> taskDAO.update(task));
    }

    public void deleteTask(Task task){
        AppExecutors.getInstance().diskIO().execute(() -> taskDAO.delete(task));
    }

    public void insertProject(Project project){
        AppExecutors.getInstance().diskIO().execute(() -> projectDAO.insert(project));
    }

    public void updateProject(Project project){
        AppExecutors.getInstance().diskIO().execute(() -> projectDAO.update(project));
    }

    public void deleteProject(Project project){
        AppExecutors.getInstance().diskIO().execute(() -> projectDAO.delete(project));
    }

    public void addFavorite(long taskId){
        AppExecutors.getInstance().diskIO().execute(() -> taskDAO.addFavorite(getUserId(), taskId));
    }

    public void removeFavorite(long taskId){
        AppExecutors.getInstance().diskIO().execute(() -> taskDAO.removeFavorite(getUserId(), taskId));
    }

    public void shareProject(long projectId){
        AppExecutors.getInstance().diskIO().execute(() -> projectDAO.shareProject(getUserId(), projectId));
    }

    public void stopSharingProject(long projectId){
        AppExecutors.getInstance().diskIO().execute(() -> projectDAO.stopSharingProject(getUserId(), projectId));
    }

}
