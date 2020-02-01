package com.example.moviedb.peopleScreen.detailInformationPeople

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.model.PersonInfoResponse
import com.example.moviedb.restAPI.API
import com.example.moviedb.restAPI.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailInformationPeopleViewModel(private var id: Long) : ViewModel() {
    private var viewModel =Job()
    private var coroutineScope = CoroutineScope(viewModel+Dispatchers.Main)

    private var _infoPeople = MutableLiveData<PersonInfoResponse>()

    val infoPeople: LiveData<PersonInfoResponse>
        get() = _infoPeople

    init {
        getInfoPeople()
    }

    private fun getInfoPeople() {
        coroutineScope.launch {
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
