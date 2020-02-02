package com.example.moviedb.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoviesDAO {

    @Query("select * from MoviesEntity")
    fun getMovies(): LiveData<List<MoviesEntity>>

    @Query("select * from MoviesEntity where id = :idMovie")
    fun getMovieById(idMovie: Long): MoviesEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: MoviesEntity)

    @Query("delete from MoviesEntity where id = :idMovie")
    fun deleteMoviesById(idMovie: Long)


}