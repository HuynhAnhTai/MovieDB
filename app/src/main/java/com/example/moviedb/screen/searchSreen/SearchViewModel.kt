package com.example.moviedb.screen.searchSreen

import android.app.Application
import androidx.lifecycle.*
import com.example.moviedb.modelAPI.MovieNowPlayingResponse
import com.example.moviedb.modelAPI.MoviesTopRatedResponse
import com.example.moviedb.modelAPI.MoviesTopRatedResults
import com.example.moviedb.repository.MovieRepository
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.*

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private var moviesRepository = MovieRepository(getApplication())
    private val _movies = MutableLiveData<List<MoviesTopRatedResults>>()

    val movies: LiveData<List<MoviesTopRatedResults>>
        get() = _movies

    var page: Int = 0

    fun getMoviesSearch(movie: String){
        page++
        viewModelScope.launch {
            var value = moviesRepository.getSearchMovie(movie,page)
            if(value.results.size>0) {
                _movies.value = value.results
            }
        }
    }
}
