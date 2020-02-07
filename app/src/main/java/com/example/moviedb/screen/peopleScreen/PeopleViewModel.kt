package com.example.moviedb.peopleScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.modelAPI.PeoplePopularResponse
import com.example.moviedb.repository.PeopleRepository
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class PeopleViewModel : ViewModel() {
    private var repositoryPeople = PeopleRepository()

    private val _people = MutableLiveData<PeoplePopularResponse>()
    var peoplePage : Int =0

    val people: LiveData<PeoplePopularResponse>
        get() = _people

    init {
        getPeoplePopular()
    }

    fun getPeoplePopular() {
        viewModelScope.launch {
            peoplePage++
            var listResult = repositoryPeople.getPeoplePopular(peoplePage)
            _people.value = listResult
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}
