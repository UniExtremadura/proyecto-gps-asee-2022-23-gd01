package es.unex.infinitetime.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FavoriteRemoteDAO {

        @GET("tab/favorites/user_id/{user_id}")
        Call<List<FavoriteRemote>> getFavorites(@Path("user_id") String userId);

        @POST("tab/favorites")
        Call<List<FavoriteRemote>> insertFavorites(@Body List<FavoriteRemote> favorites);

        @DELETE("tab/favorites/user_id/{user_id}")
        Call<List<FavoriteRemote>> deleteFavorites(@Path("user_id") String userId);

}
