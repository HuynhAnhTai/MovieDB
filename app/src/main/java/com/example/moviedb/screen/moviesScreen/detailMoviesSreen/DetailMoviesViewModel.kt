package com.example.moviedb.moviesScreen.detailMoviesSreen

import android.app.Application
import androidx.lifecycle.*
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.modelAPI.CreditByIdFilmResponse
import com.example.moviedb.modelAPI.MovieByIdResponse
import com.example.moviedb.modelAPI.Videos
import com.example.moviedb.repository.MovieRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*
import java.lang.Exception

class DetailMoviesViewModel(application: Application) : AndroidViewModel(application) {
    private var movieRepository = MovieRepository(getApplication())

    private var movieRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _detailMovie = MutableLiveData<MovieByIdResponse>()

    val detailMovie: LiveData<MovieByIdResponse>
        get() = _detailMovie

    var _id: Long = 0
        set(value) {
            field = value
            getDetailMovie()
        }


    private val _creditMovie = MutableLiveData<CreditByIdFilmResponse>()

    val castMovie: LiveData<CreditByIdFilmResponse>
        get() = _creditMovie

    private val _save = MutableLiveData<MoviesEntity>()

    val save: LiveData<MoviesEntity>
        get() = _save

    private var _back = MutableLiveData<Boolean>()

    val back : LiveData<Boolean>
        get() = _back

    private fun getDetailMovie() {
        viewModelScope.launch {
            try{
                var result = movieRepository.getDetailMovieById(_id)
                _detailMovie.value = result
                getCastMovie()
            }
            catch (e: Exception){
                _detailMovie.value = MovieByIdResponse(false,"",ArrayList(),
                    0,"","","",0F,Videos(ArrayList()))
            }
        }
    }

    private fun getCastMovie(){
        viewModelScope.launch {
            try{
                var result = movieRepository.getCastMovieByIdFilm(_id)
                _creditMovie.value = result
                checkSave()
            }
            catch (e: Exception){
                _creditMovie.value = CreditByIdFilmResponse(0,ArrayList())
            }
        }
    }

    private fun checkSave(){
        viewModelScope.launch {
            var value1 = movieRepository.checkSaveFilm(_id)
            if (value1==null){
                _save.value =  MoviesEntity(false,"","",-1,"","","",0F)
            }else{
                _save.value = value1
            }
        }
    }


    fun insertMovie(moviesEntity: MoviesEntity){
        viewModelScope.launch {
            uploadDatabase(moviesEntity)
            movieRepository.insertMovieToDB(moviesEntity)
        }
    }


    fun uploadDatabase(moviesEntity: MoviesEntity){
        var hashMap = HashMap<String,Any>()
        hashMap.put("id",moviesEntity.id)
        hashMap.put("poster_path",moviesEntity.poster_path)
        hashMap.put("backdrop_path",moviesEntity.backdrop_path)
        hashMap.put("genres",moviesEntity.genres)
        hashMap.put("overview",moviesEntity.overview)
        hashMap.put("title",moviesEntity.title)
        hashMap.put("adult",moviesEntity.adult)
        hashMap.put("vote_average",moviesEntity.vote_average)

        movieRef.child("SaveMovie").child(mAuth.currentUser!!.uid)
            .child(moviesEntity.id.toString()).updateChildren(hashMap).addOnCompleteListener {
                if (it.isSuccessful) {
                    _back.value = true
                }
            }
    }


    fun deleteMovie(moviesEntity: MoviesEntity){
        viewModelScope.launch {
            movieRef.child("SaveMovie").child(mAuth.currentUser!!.uid)
                .child(moviesEntity.id.toString()).removeValue()

            movieRepository.deleteMovieFromDB(moviesEntity.id)

            _back.value = true
        }

    }

}
