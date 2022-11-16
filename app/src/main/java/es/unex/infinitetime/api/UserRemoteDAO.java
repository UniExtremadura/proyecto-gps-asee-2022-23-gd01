package es.unex.infinitetime.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserRemoteDAO {

    @GET("tab/users/id/{id}")
    public Call<UserRemote> getUser(@Path("id") String id);

    @POST("tab/users")
    public Call<UserRemote> insertUser(@Body UserRemote user);

    @DELETE("tab/users/id/{id}")
    public Call<UserRemote> deleteUser(@Path("id") String id);

}
