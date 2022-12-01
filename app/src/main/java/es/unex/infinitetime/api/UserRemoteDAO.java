package es.unex.infinitetime.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserRemoteDAO {

    @GET("tabs/users")
    Call<List<UserRemote>> getUsers();

    @POST("tabs/users")
    Call<List<UserRemote>> insertUsers(@Body List<UserRemote> users);

    @DELETE("tabs/users/search?id=*")
    Call<List<UserRemote>> deleteUsers();

}
