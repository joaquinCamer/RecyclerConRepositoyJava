package com.androidavanzado.offlinemovies.Retrofit;

import com.androidavanzado.offlinemovies.pojo.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApiService {

    @GET("movie/popular")
    Call<MoviesResponse> loadPopularMovies();
}
