package es.unex.infinitetime.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SharedProjectRemoteDAO {

        @GET("tab/shared_projects")
        Call<List<SharedProjectRemote>> getSharedProjects();

        @POST("tab/shared_projects")
        Call<List<SharedProjectRemote>> insertSharedProjects(@Body List<SharedProjectRemote> sharedProjects);

        @DELETE("tab/shared_projects/user_id/{user_id}")
        Call<List<SharedProjectRemote>> deleteSharedProjects();

}
