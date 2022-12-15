package es.unex.infinitetime.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {


    @Query("SELECT * FROM task")
    List<Task> getAllTasks();

    @Query("SELECT COUNT (*) FROM task WHERE user_id=:userId AND state=:state")
    LiveData<Integer> getTasksNum(long userId, int state);

    @Query("SELECT id, name, description, state, priority, effort, deadline, project_id, user_id, EXISTS(SELECT 1 FROM favorite WHERE task_id=id and project_id=:projectId) AS is_favorite FROM task WHERE project_id=:projectId and state=:state ORDER BY priority DESC")
    LiveData<List<TaskWithFavorite>> getTasksProject(long projectId, int state);

    @Query("INSERT INTO favorite (user_id, task_id) VALUES (:userId, :taskId)")
    void addFavorite(long userId, long taskId);

    @Query("DELETE FROM favorite WHERE user_id = :userId AND task_id = :taskId")
    void removeFavorite(long userId, long taskId);

    @Query("SELECT * FROM favorite WHERE user_id = :userId AND task_id = :taskId")
    Favorite getFavorite(long userId, long taskId);

    @Insert
    void insert(Task task);

    @Update
    int update(Task task);

    @Query("DELETE FROM task WHERE id = :taskId")
    void delete(long taskId);

    @Query("DELETE FROM task")
    void deleteAllTasks();
}
