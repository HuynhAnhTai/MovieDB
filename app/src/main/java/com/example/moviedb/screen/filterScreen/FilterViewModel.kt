package com.example.moviedb.filterScreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.db.FilterEntity
import com.example.moviedb.db.getDatabaseMovie
import kotlinx.coroutines.*

class FilterViewModel(private var context: Context) : ViewModel() {
    // TODO: Implement the ViewModel
    private var viewModel = Job()
    private var coroutineScope = CoroutineScope(viewModel + Dispatchers.Main)

    private var _filter = MutableLiveData<FilterEntity>()

    val filter: LiveData<FilterEntity>
        get() = _filter
    init {
        getFilter()
    }

    fun getFilter() {
        coroutineScope.launch {
            var result = FilterEntity(0,"", "","","")
            withContext(Dispatchers.IO){
                result = getDatabaseMovie(context).dao.getFilterById(1)
            }
            if(result==null){
                _filter.value = FilterEntity(0,"", "","","")
            }else{
                _filter.value = result
            }
        }
    }

    fun insertFilter(sortBy: String, startTime:String, endTime: String, genres: String){
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                var filter = FilterEntity(1, sortBy, startTime, endTime, genres)
                getDatabaseMovie(context).dao.insertFilter(filter)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModel.cancel()
    }
}
