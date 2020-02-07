package com.example.moviedb.filterScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.moviedb.db.FilterEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.repository.FilterRepository
import kotlinx.coroutines.*

class FilterViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private var filterRepository = FilterRepository(getApplication())

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
            result = filterRepository.getFilterByIdRoomDB()
            _filter.value = result
        }
    }

    fun insertFilter(sortBy: String, startTime:String, endTime: String, genres: String){
        viewModelScope.launch {
            var filter = FilterEntity(1, sortBy, startTime, endTime, genres)
            filterRepository.insertFilterToRoomDB(filter)
            check = 1
            _back.value = true
        }
    }
}
