package com.androidavanzado.offlinemovies.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.androidavanzado.offlinemovies.pojo.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {

    public abstract MovieDao getMovieDao();
}
