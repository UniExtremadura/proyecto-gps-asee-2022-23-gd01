package es.unex.infinitetime.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProjectRemoteDAO {

    @GET("tabs/projects")
    Call<List<ProjectRemote>> getProjects();

    @POST("tabs/projects")
    Call<List<ProjectRemote>> insertProjects(@Body List<ProjectRemote> projects);

    @DELETE("tabs/projects/search?id=*")
    Call<List<ProjectRemote>> deleteProjects();
}
