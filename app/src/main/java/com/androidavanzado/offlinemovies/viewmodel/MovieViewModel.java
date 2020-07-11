package com.androidavanzado.offlinemovies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidavanzado.offlinemovies.repository.MovieRepository;
import com.androidavanzado.offlinemovies.pojo.MovieEntity;
import com.androidavanzado.offlinemovies.repository.Resource;

import java.util.List;

public class MovieViewModel extends ViewModel {


    //resource porque no sabemos si es lcoal o remoto
    private final LiveData<Resource<List<MovieEntity>>> popularMovies;
    private MovieRepository movieRepository;

    public MovieViewModel() {
        movieRepository = new MovieRepository();
        popularMovies = movieRepository.getPopularMovies();
    }

    public LiveData<Resource<List<MovieEntity>>> getPopularMovies() {
        return popularMovies;
    }


}
