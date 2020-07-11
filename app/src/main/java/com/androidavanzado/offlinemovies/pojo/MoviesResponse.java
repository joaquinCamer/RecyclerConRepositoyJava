package com.androidavanzado.offlinemovies.pojo;

import com.androidavanzado.offlinemovies.pojo.MovieEntity;

import java.util.List;


// entidad padre para obtener el  pojo
public class MoviesResponse {
    private List<MovieEntity> results;

    public List<MovieEntity> getResults() {
        return results;
    }

    public void setResults(List<MovieEntity> results) {
        this.results = results;
    }
}
