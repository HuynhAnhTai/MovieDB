package com.example.moviedb.peopleScreen.detailInformationPeople

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.modelAPI.PersonInfoResponse
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailInformationPeopleViewModel(private var id: Long) : ViewModel() {

    private var _infoPeople = MutableLiveData<PersonInfoResponse>()

    val infoPeople: LiveData<PersonInfoResponse>
        get() = _infoPeople

    init {
        getInfoPeople()
    }

    private fun getInfoPeople() {
        viewModelScope.launch {
            try{
                var result = API.RETROFIT_SERVICE.getInfoPeople(id).await()
                _infoPeople.value = result
            }
            catch (e: Exception){
                _infoPeople.value = PersonInfoResponse(0,"","","","","")
            }
        }
    }
}
