package es.unex.infinitetime.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SharedProjectRemoteDAO {

        @GET("tab/shared_projects/user_id/{user_id}")
        Call<List<SharedProjectRemote>> getSharedProjectsByUser(@Path("user_id") String userId);

        @POST("tab/shared_projects")
        Call<List<SharedProjectRemote>> insertSharedProjects(@Body List<SharedProjectRemote> sharedProjects);

        @DELETE("tab/shared_projects/user_id/{user_id}")
        Call<List<SharedProjectRemote>> deleteSharedProjectsByUser(@Path("user_id") String userId);

}
