package com.example.moviedb.seriesScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.model.SeriesTopRatedResponse
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class SeriesViewModel : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _series = MutableLiveData<SeriesTopRatedResponse>()

    val series: LiveData<SeriesTopRatedResponse>
        get() = _series

    init {
        getSeriesTopRated()
    }

    private fun getSeriesTopRated() {
        coroutineScope.launch {
            try{
                var listResult = API.RETROFIT_SERVICE.getSeriesTopRated().await()
                _series.value = listResult
            }
            catch (e: Exception){
                _series.value = SeriesTopRatedResponse(0,0,0,ArrayList())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
