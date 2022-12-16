package es.unex.infinitetime.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.api.RemoteDAOs;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.model.Project;
import es.unex.infinitetime.model.ProjectDAO;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskDAO;
import es.unex.infinitetime.model.TaskWithFavorite;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.model.UserDAO;
import es.unex.infinitetime.model.UserShared;

public class Repository {

    private static Repository instance;
    private PersistenceUser persistenceUser;

    private final UploadToAPI uploadToAPI;
    private final DownloadFromAPI downloadFromAPI;

    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;

    private final MutableLiveData<Long> userId;
    private final MutableLiveData<Long> taskProjectId;
    private final MutableLiveData<Long> sharedProjectId;

    private final LiveData<User> user;
    private final LiveData<List<Project>> projects;
    private final LiveData<List<Task>> favoriteTasks;
    private final LiveData<List<UserShared>> usersShared;

    private final LiveData<List<TaskWithFavorite>> taskToDo;
    private final LiveData<List<TaskWithFavorite>> taskDoing;
    private final LiveData<List<TaskWithFavorite>> taskDone;

    private final LiveData<Integer> tasksNumToDo;
    private final LiveData<Integer> tasksNumDoing;
    private final LiveData<Integer> tasksNumDone;

    private Repository(InfiniteDatabase database, RemoteDAOs remoteDAOs, PersistenceUser persistenceUser) {

        this.persistenceUser = persistenceUser;

        userDAO = database.userDAO();
        projectDAO = database.projectDAO();
        taskDAO = database.taskDAO();

        userId = new MutableLiveData<>();
        taskProjectId = new MutableLiveData<>();
        sharedProjectId = new MutableLiveData<>();

        downloadFromAPI = new DownloadFromAPI(database, remoteDAOs);
        uploadToAPI = new UploadToAPI(database, remoteDAOs);

        user = Transformations.switchMap(getUserId(), userDAO::getUser);
        projects = Transformations.switchMap(getUserId(), userDAO::getAllProjectsOfUser);
        favoriteTasks = Transformations.switchMap(getUserId(), userDAO::getFavoriteTasks);

        usersShared = Transformations.switchMap(sharedProjectId, userDAO::getUsersShared);

        taskToDo = Transformations.switchMap(taskProjectId, (projectId) ->taskDAO.getTasksProject(projectId, 0));
        taskDoing = Transformations.switchMap(taskProjectId, (projectId) ->taskDAO.getTasksProject(projectId, 1));
        taskDone = Transformations.switchMap(taskProjectId, (projectId) ->taskDAO.getTasksProject(projectId, 2));

        tasksNumToDo = Transformations.switchMap(getUserId(), userId -> taskDAO.getTasksNum(userId, 0));
        tasksNumDoing = Transformations.switchMap(getUserId(), userId -> taskDAO.getTasksNum(userId, 1));
        tasksNumDone = Transformations.switchMap(getUserId(), userId -> taskDAO.getTasksNum(userId, 2));
    }

    public static Repository getInstance(InfiniteDatabase database, RemoteDAOs remoteDAOs, PersistenceUser persistenceUser) {
        if (instance == null) {
            instance = new Repository(database, remoteDAOs, persistenceUser);
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

    public void setTaskProjectId(long projectId) {
        this.taskProjectId.setValue(projectId);
    }

    public void setSharedProjectId(long projectId) {
        this.sharedProjectId.setValue(projectId);
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

    public LiveData<List<TaskWithFavorite>> getTasksToDo() {
        return taskToDo;
    }

    public LiveData<List<TaskWithFavorite>> getTasksDoing() {
        return taskDoing;
    }

    public LiveData<List<TaskWithFavorite>> getTasksDone() {
        return taskDone;
    }

    public LiveData<List<UserShared>> getUsersShared(){
        return usersShared;
    }

    public LiveData<Long> getTaskProjectId() {
        return taskProjectId;
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

    public boolean userExists(String username){
        return userDAO.usernameExists(username);
    }

    public User getUserByUsername(String username){
        return userDAO.getUser(username);
    }

    public User getUserWithoutLiveData(){
        return userDAO.getUserWithoutLiveData(getUserId().getValue());
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
        AppExecutors.getInstance().diskIO().execute(() -> taskDAO.addFavorite(getUserId().getValue(), taskId));
    }

    public void removeFavorite(long taskId){
        AppExecutors.getInstance().diskIO().execute(() -> taskDAO.removeFavorite(getUserId().getValue(), taskId));
    }

    public void shareProject(long userId){
        AppExecutors.getInstance().diskIO().execute(() -> projectDAO.shareProject(sharedProjectId.getValue(), userId));
    }

    public void stopSharingProject(long userId){
        AppExecutors.getInstance().diskIO().execute(() -> projectDAO.stopSharingProject(sharedProjectId.getValue(), userId));
    }

    public void closeSession(){
        persistenceUser.deleteUserId();
    }

    public void openSession(long userId) {
        persistenceUser.setUserId(userId);
        persistenceUser.saveUserId();
        setUserId(userId);
        setTaskProjectId(0);
    }

    public boolean isSessionOpen() {
        boolean opened = persistenceUser.isSessionOpen();
        if (opened) {
            setUserId(persistenceUser.getUserId());
        }
        return opened;
    }
}
