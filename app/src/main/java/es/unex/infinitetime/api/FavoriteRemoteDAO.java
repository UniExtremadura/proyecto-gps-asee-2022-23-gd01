package es.unex.infinitetime.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FavoriteRemoteDAO {

        @GET("tabs/favorite")
        Call<List<FavoriteRemote>> getFavorites();

        @POST("tabs/favorites")
        Call<List<FavoriteRemote>> insertFavorites(@Body List<FavoriteRemote> favorites);

        @DELETE("tabs/favorite")
        Call<List<FavoriteRemote>> deleteFavorites();

}
