package com.example.moviedb.seriesScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.modelAPI.SeriesTopRatedResponse
import com.example.moviedb.repository.SeriesRepository
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class SeriesViewModel : ViewModel() {
    private var seriesRepository = SeriesRepository()

    private val _series = MutableLiveData<SeriesTopRatedResponse>()

    val series: LiveData<SeriesTopRatedResponse>
        get() = _series

    init {
        getSeriesTopRated()
    }
    var serviecePage: Int = 0
    fun getSeriesTopRated() {
        viewModelScope.launch {
            try{
                serviecePage++
                var listResult = seriesRepository.getSeriesTopRate(serviecePage)
                _series.value = listResult
            }
            catch (e: Exception){
                _series.value = SeriesTopRatedResponse(0,0,0,ArrayList())
            }
        }
    }
}
