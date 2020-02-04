package com.example.moviedb.peopleScreen.detailInformationPeople

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailInformationPeopleViewModelFactory (private var id: Long): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailInformationPeopleViewModel::class.java)){
            return DetailInformationPeopleViewModel(id) as T
        }
        throw IllegalArgumentException("Unknow View Model")
    }

}