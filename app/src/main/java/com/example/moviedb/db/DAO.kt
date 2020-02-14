package com.example.moviedb.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviedb.modelAPI.Genres

@Dao
interface DAO {

    @Query("select * from MoviesEntity")
    fun getMovies(): LiveData<List<MoviesEntity>>

    @Query("select * from MoviesEntity where id = :idMovie")
    fun getMovieById(idMovie: Long): MoviesEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: MoviesEntity)

    @Query("delete from MoviesEntity where id = :idMovie")
    fun deleteMoviesById(idMovie: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilter(filter: FilterEntity)

    @Query("select * from FilterEntity where id = :idFilter")
    fun getFilterById(idFilter: Int): FilterEntity

    @Query("select * from FilterEntity")
    fun getFilter(): LiveData<FilterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: ArrayList<GenresEntity>)

    @Query("select * from GenresEntity")
    fun getGenres(): LiveData<List<GenresEntity>>

    @Query("DELETE FROM MoviesEntity")
    fun deleteAllMovieEntity()

}