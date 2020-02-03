package com.example.moviedb.peopleScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.modelAPI.PeoplePopularResponse
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class PeopleViewModel : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _people = MutableLiveData<PeoplePopularResponse>()

    val people: LiveData<PeoplePopularResponse>
        get() = _people

    init {
        getPeoplePopular()
    }

    private fun getPeoplePopular() {
        coroutineScope.launch {
            try{
                var listResult = API.RETROFIT_SERVICE.getPeoplePopular().await()
                _people.value = listResult
            }
            catch (e: Exception){
                _people.value = PeoplePopularResponse(0,0,0,ArrayList())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
