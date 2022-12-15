package es.unex.infinitetime.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.model.Project;
import es.unex.infinitetime.model.ProjectDAO;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskDAO;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.model.UserDAO;
import es.unex.infinitetime.model.UserShared;

public class Repository {

    private static Repository instance;

    private final UploadToAPI uploadToAPI;
    private final DownloadFromAPI downloadFromAPI;

    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;

    private final MutableLiveData<Long> userId;
    private final MutableLiveData<Long> projectId;

    private final LiveData<User> user;
    private final LiveData<List<Project>> projects;
    private final LiveData<List<Task>> favoriteTasks;
    private final LiveData<List<UserShared>> usersShared;

    private final LiveData<Integer> tasksNumToDo;
    private final LiveData<Integer> tasksNumDoing;
    private final LiveData<Integer> tasksNumDone;

    private Repository() {

        ContainerRepository containerRepository = ContainerRepository.getInstance();

        userDAO = containerRepository.getUserDAO();
        projectDAO = containerRepository.getProjectDAO();
        taskDAO = containerRepository.getTaskDAO();

        userId = new MutableLiveData<>();
        projectId = new MutableLiveData<>();

        downloadFromAPI = new DownloadFromAPI();
        uploadToAPI = new UploadToAPI();

        user = Transformations.switchMap(getUserId(), userDAO::getUser);
        projects = Transformations.switchMap(getUserId(), userDAO::getAllProjectsOfUser);
        favoriteTasks = Transformations.switchMap(getUserId(), userDAO::getFavoriteTasks);
        usersShared = Transformations.switchMap(getProjectId(), userDAO::getUsersShared);

        tasksNumToDo = Transformations.switchMap(getUserId(), userId -> taskDAO.getTasksNum(userId, 0));
        tasksNumDoing = Transformations.switchMap(getUserId(), userId -> taskDAO.getTasksNum(userId, 1));
        tasksNumDone = Transformations.switchMap(getUserId(), userId -> taskDAO.getTasksNum(userId, 2));
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

    public void setProjectId(long projectId) {
        this.projectId.setValue(projectId);
    }

    public LiveData<User> getUser(){
        return user;
    }

    public LiveData<List<Project>> getAllProjectsUser(){
        return projects;
    }

    public LiveData<List<Task>> getFavoriteTasks() {
        return favoriteTasks;
    }

    public LiveData<List<Task>> getTasksProject(long projectId) {
        return taskDAO.getTasksProject(projectId);
    }

    public boolean userExists(String username){
        return userDAO.usernameExists(username);
    }

    public LiveData<List<UserShared>> getUsersShared(){
        return usersShared;
    }

    private LiveData<Long> getProjectId() {
        return projectId;
    }

    public LiveData<Integer> getTasksNumToDo(){
        return tasksNumToDo;
    }

    public LiveData<Integer> getTasksNumDoing(){
        return tasksNumDoing;
    }

    public LiveData<Integer> getTasksNumDone(){
        return tasksNumDone;
    }

    public boolean isInFavorite(long taskId){
        return taskDAO.getFavorite(getUserId().getValue(), taskId) != null;
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

    public void shareProject(long userId){
        AppExecutors.getInstance().diskIO().execute(() -> projectDAO.shareProject(projectId.getValue(), userId));
    }

    public void stopSharingProject(long userId){
        AppExecutors.getInstance().diskIO().execute(() -> projectDAO.stopSharingProject(projectId.getValue(), userId));
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
