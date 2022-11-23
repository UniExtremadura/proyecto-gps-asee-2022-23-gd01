package es.unex.infinitetime.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TaskRemoteDAO {

    @GET("tabs/tasks")
    public Call<List<TaskRemote>> getTasks();

    @POST("tabs/tasks")
    public Call<List<TaskRemote>> insertTasks(@Body List<TaskRemote> tasks);

    @DELETE("tabs/tasks")
    public Call<List<TaskRemote>> deleteTasks();

}
