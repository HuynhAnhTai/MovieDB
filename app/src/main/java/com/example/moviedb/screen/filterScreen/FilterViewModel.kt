package com.example.moviedb.filterScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.moviedb.db.FilterEntity
import com.example.moviedb.db.getDatabaseMovie
import kotlinx.coroutines.*

class FilterViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    private var _filter = MutableLiveData<FilterEntity>()

    val filter: LiveData<FilterEntity>
        get() = _filter

    private var _back = MutableLiveData<Boolean>()

    val back: LiveData<Boolean>
        get() = _back

    private var check: Int = 0
    init {
        getFilter()
    }

    fun getFilter() {
        viewModelScope.launch {
            var result = FilterEntity(0,"", "","","")
            withContext(Dispatchers.IO){
                result = getDatabaseMovie(getApplication()).dao.getFilterById(1)
            }
            if(result==null){
                _filter.value = FilterEntity(0,"", "","","")
            }else{
                _filter.value = result
            }
        }
    }

    fun insertFilter(sortBy: String, startTime:String, endTime: String, genres: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                var filter = FilterEntity(1, sortBy, startTime, endTime, genres)
                getDatabaseMovie(getApplication()).dao.insertFilter(filter)
                check = 1
            }
            if (check == 1){
                _back.value = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}