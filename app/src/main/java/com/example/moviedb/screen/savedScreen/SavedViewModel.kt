package com.example.moviedb.screen.savedScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.repository.MovieRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.lifecycle.viewModelScope
import com.example.moviedb.modelFirebase.MovieEntityFirebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class SavedViewModel(application: Application) : AndroidViewModel(application) {
    private var moviesRepository = MovieRepository(getApplication())
    val movieSave : LiveData<List<MoviesEntity>> = moviesRepository.movieSave

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var movieRef: DatabaseReference = FirebaseDatabase.getInstance().reference

    private var listIdMovieFirebase: MutableList<String> = ArrayList()

    private var entityMovieFirebase: MoviesEntity = MoviesEntity(false,"","",0,"","","",0F)
    fun checkDataFirebase(){
        movieRef.child("SaveMovie").child(mAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (i in dataSnapshot.children){
                    listIdMovieFirebase.add(i.key.toString())
                }
                for (i in dataSnapshot.children){
                    var movieFirebase = i.getValue(MovieEntityFirebase::class.java)!!

                    entityMovieFirebase = MoviesEntity(movieFirebase.adult,movieFirebase.backdrop_path,
                        movieFirebase.genres, movieFirebase.id,movieFirebase.overview, movieFirebase.poster_path,
                        movieFirebase.title, movieFirebase.vote_average)

                    viewModelScope.launch {
                        moviesRepository.insertMovieToDB(entityMovieFirebase)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun conpareFlim(dataRoom: List<MoviesEntity>){
        for (i in dataRoom){
            if (!listIdMovieFirebase.contains(i.id.toString())){
                viewModelScope.launch {
                    moviesRepository.deleteMovieFromDB(i.id)
                }
            }
        }
    }
}
