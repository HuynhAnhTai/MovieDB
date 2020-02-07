package com.example.moviedb.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.modelAPI.CreditByIdFilmResponse
import com.example.moviedb.modelAPI.MovieByIdResponse
import com.example.moviedb.modelAPI.MoviesTopRatedResponse
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MovieRepository (private val application: Application){

    val movieSave : LiveData<List<MoviesEntity>> = getDatabaseMovie(application).dao.getMovies()

    suspend fun getMovieBetweenDate(sortBy: String, moviePage: Int, startTime: String, endTime: String, genres: String): MoviesTopRatedResponse {
        var value = MoviesTopRatedResponse(0,0,0,ArrayList())
        withContext(Dispatchers.IO){
            if (sortBy.equals("title") || sortBy.equals("")){
                value = API.RETROFIT_SERVICE
                    .getFilterFlimBetweenDates("original_title.asc",
                        moviePage, startTime,
                        endTime,
                        genres)
                    .await()
            }else{
                value = API.RETROFIT_SERVICE
                    .getFilterFlimBetweenDates(sortBy+".desc",
                        moviePage, startTime,
                        endTime,
                        genres)
                    .await()
            }
        }
        return value
    }

    suspend fun getMovieTheater(sortBy: String, moviePage: Int, genres: String): MoviesTopRatedResponse {
        var value = MoviesTopRatedResponse(0,0,0,ArrayList())
        withContext(Dispatchers.IO){
            if (sortBy.equals("title") || sortBy.equals("")){
                    value = API.RETROFIT_SERVICE
                        .getFilterFlimTheatre(
                            "original_title.asc",
                            moviePage,
                            genres
                        )
                        .await()
            }else{
                value = API.RETROFIT_SERVICE
                    .getFilterFlimTheatre(
                        sortBy + ".desc",
                        moviePage,
                        genres
                    ).await()
            }
        }
        return value
    }

    suspend fun getDetailMovieById(id: Long): MovieByIdResponse {
        var value = MovieByIdResponse(false,"",ArrayList(),id,"","","",0F)
        withContext(Dispatchers.IO){
            value = API.RETROFIT_SERVICE.getDetailMovieById(id).await()
        }
        return value
    }

    suspend fun getCastMovieByIdFilm(id: Long): CreditByIdFilmResponse {
        var value = CreditByIdFilmResponse(0, ArrayList())
        withContext(Dispatchers.IO){
            value = API.RETROFIT_SERVICE.getCastMovie(id).await()
        }
        return value
    }

    suspend fun checkSaveFilm(id: Long): MoviesEntity {
        var value = MoviesEntity(false,"","",id,"","","",0F)
        withContext(Dispatchers.IO){
             value = getDatabaseMovie(application).dao.getMovieById(id)
        }
        return value
    }

    suspend fun insertMovieToDB(moviesEntity: MoviesEntity){
        withContext(Dispatchers.IO){
            getDatabaseMovie(application).dao.insertMovies(moviesEntity)
        }
    }

    suspend fun deleteMovieFromDB(id: Long){
        withContext(Dispatchers.IO){
            getDatabaseMovie(application).dao.deleteMoviesById(id)
        }
    }

    suspend fun getMoviesByIdFromDB(id: Long): MoviesEntity {
        var value = MoviesEntity(false,"","",id,"","","",0F)
        withContext(Dispatchers.IO){
            value = getDatabaseMovie(application).dao.getMovieById(id)
        }
        return value
    }

    suspend fun getSearchMovie(nameMovie: String, page: Int): MoviesTopRatedResponse {
        var value = MoviesTopRatedResponse(0,0,0,ArrayList())
        withContext(Dispatchers.IO){
            value = API.RETROFIT_SERVICE.getSearchFlim(nameMovie, page).await()
        }
        return value
    }
}