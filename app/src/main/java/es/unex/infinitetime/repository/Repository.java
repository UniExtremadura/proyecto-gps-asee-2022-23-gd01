package es.unex.infinitetime.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.model.Project;
import es.unex.infinitetime.model.ProjectDAO;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskDAO;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.model.UserDAO;
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

    private final MutableLiveData<Long> userId;

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
        userId = new MutableLiveData<>();
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

    public LiveData<Long> getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId.setValue(userId);
    }

    public LiveData<User> getUser(){
        return Transformations.switchMap(getUserId(), userDAO::getUser);
    }

    public LiveData<List<Project>> getAllProjectsUser(){
        return Transformations.switchMap(getUserId(), userDAO::getAllProjectsOfUser);
    }

    public LiveData<List<Task>> getFavoriteTasks() {
        return Transformations.switchMap(getUserId(), userDAO::getFavoriteTasks);
    }

    public LiveData<List<Task>> getTasksProject(long projectId) {
        return taskDAO.getTasksProject(projectId);
    }

    public boolean userExists(String username){
        return userDAO.getUser(username) != null;
    }

    public LiveData<List<User>> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public LiveData<Integer> getTasksNumByState(int state){
        return Transformations.switchMap(getUserId(), userId -> taskDAO.getTasksNum(userId, state));
    }

    public boolean isInFavorite(long taskId){
        return taskDAO.getFavorite(getUserId().getValue(), taskId) != null;
    }

    public boolean isShared(long userId, long projectId){
        return projectDAO.getSharedProject(userId, projectId) != null;
    }

    public void insertUser(User user){
        AppExecutors.getInstance().diskIO().execute(() -> userDAO.insert(user));
    }

    public void updateUser(User user){
        AppExecutors.getInstance().diskIO().execute(() -> userDAO.update(user));
    }

    public void deleteUser(long userId){
        AppExecutors.getInstance().diskIO().execute(() -> userDAO.delete(userId));
    }

    public void insertTask(Task task){
        AppExecutors.getInstance().diskIO().execute(() -> taskDAO.insert(task));
    }

    public void updateTask(Task task){
        AppExecutors.getInstance().diskIO().execute(() -> taskDAO.update(task));
    }

    public void deleteTask(long taskId){
        AppExecutors.getInstance().diskIO().execute(() -> taskDAO.delete(taskId));
    }

    public void insertProject(Project project){
        AppExecutors.getInstance().diskIO().execute(() -> projectDAO.insert(project));
    }

    public void updateProject(Project project){
        AppExecutors.getInstance().diskIO().execute(() -> projectDAO.update(project));
    }

    public void deleteProject(long projectId){
        AppExecutors.getInstance().diskIO().execute(() -> projectDAO.delete(projectId));
    }

    public void addFavorite(long taskId){
        taskDAO.addFavorite(getUserId().getValue(), taskId);
    }

    public void removeFavorite(long taskId){
        taskDAO.removeFavorite(getUserId().getValue(), taskId);
    }

    public void shareProject(long userId, long projectId){
        projectDAO.shareProject(projectId, userId);
    }

    public void stopSharingProject(long userId, long projectId){
        projectDAO.stopSharingProject(userId, projectId);
    }

    public User getUserByUsername(String username){
        return userDAO.getUser(username);
    }

    public User getUserWithoutLiveData(){
        return userDAO.getUserWithoutLiveData(getUserId().getValue());
    }

    public void closeSession(){
        PersistenceUser.getInstance().deleteUserId();
    }

    public void openSession(long userId){
        PersistenceUser.getInstance().setUserId(userId);
        PersistenceUser.getInstance().saveUserId();
        setUserId(userId);
    }

    public boolean isSessionOpen() {
        boolean opened = PersistenceUser.getInstance().isSessionOpen();
        if (opened) {
            setUserId(PersistenceUser.getInstance().getUserId());
        }
        return opened;
    }
}
