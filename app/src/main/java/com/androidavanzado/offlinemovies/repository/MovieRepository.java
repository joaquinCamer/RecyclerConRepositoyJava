package com.androidavanzado.offlinemovies.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.androidavanzado.offlinemovies.app.MyApp;
import com.androidavanzado.offlinemovies.Room.MovieRoomDatabase;
import com.androidavanzado.offlinemovies.Room.MovieDao;
import com.androidavanzado.offlinemovies.pojo.MovieEntity;
import com.androidavanzado.offlinemovies.app.ApiConstants;
import com.androidavanzado.offlinemovies.Retrofit.MovieApiService;
import com.androidavanzado.offlinemovies.Retrofit.RequestInterceptor;
import com.androidavanzado.offlinemovies.pojo.MoviesResponse;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    // obtener interface de retrofit
    private final MovieApiService movieApiService;

    //obtener interface  de room
    private final MovieDao movieDao;

    public MovieRepository() {
        // Acceso a la base de datos local
        MovieRoomDatabase movieRoomDatabase = Room.databaseBuilder(
                MyApp.getContext(),
                MovieRoomDatabase.class,
                "db_movies"
        ).build();
        movieDao = movieRoomDatabase.getMovieDao();
//--------------------------------------------------------------------------------

        // RequestInterceptor: incluir en la cabecera (URL) de la
        // petición el TOKEN o API_KEY que autoriza al usuario
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new RequestInterceptor());
        OkHttpClient cliente = okHttpClientBuilder.build();

        // Acceso a la base de datos remota
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(cliente)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApiService = retrofit.create(MovieApiService.class);

    }
//----------------------------------------------------------------------

    public LiveData<Resource<List<MovieEntity>>> getPopularMovies() {
        // devuelve el network bound resource
        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>() {

            @Override
            protected void saveCallResult(@NonNull MoviesResponse item) {
                movieDao.saveMovies(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                // los datos que dispongamos en Room, en la BD local
                return movieDao.loadMovies();
            }

            @NonNull
            @Override
            protected Call<MoviesResponse> createCall() {
                // obtenemos los datos de la API remota
                return movieApiService.loadPopularMovies();
            }
        }.getAsLiveData();
    }
}
