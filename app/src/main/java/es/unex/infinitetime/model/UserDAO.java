package es.unex.infinitetime.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user WHERE username = :username")
    User getUser(String username);

    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<User> getUser(long userId);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM project WHERE user_id = :userId")
    LiveData<List<Project>> getProjectsCreated(long userId);

    @Query("SELECT * FROM project WHERE id IN (SELECT project_id FROM shared_project WHERE user_id = :userId)")
    LiveData<List<Project>> getProjectsShared(long userId);

    @Query("SELECT * FROM project WHERE id IN (SELECT project_id FROM shared_project WHERE user_id = :userId) OR user_id = :userId")
    LiveData<List<Project>> getAllProjectsUser(long userId);

    @Query("SELECT * FROM task WHERE id IN (SELECT task_id FROM favorite WHERE user_id = :userId)")
    LiveData<List<Task>> getFavoriteTasks(long userId);

    @Insert
    void insert(User user);

    @Insert
    void insertFavorite(Favorite favorite);

    @Insert
    void insertSharedProject(SharedProject sharedProject);

    @Update
    int update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAllUsers();

    @Query("DELETE FROM favorite")
    void deleteAllFavorites();

    @Query("DELETE FROM shared_project")
    void deleteAllSharedProjects();
}
