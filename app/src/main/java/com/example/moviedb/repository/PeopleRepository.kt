package com.example.moviedb.repository

import com.example.moviedb.modelAPI.PeoplePopularResponse
import com.example.moviedb.modelAPI.PersonInfoResponse
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class PeopleRepository(){

    suspend fun getPeoplePopular(peoplePage: Int): PeoplePopularResponse {
        var value = PeoplePopularResponse(0, 0,0,ArrayList())
        withContext(Dispatchers.IO){
            try {
                value = API.RETROFIT_SERVICE.getPeoplePopular(peoplePage).await()
            }catch (e: Exception){
                value = PeoplePopularResponse(0,0,0,ArrayList())
            }
        }
        return value
    }

    suspend fun getInfoPeopleById(id: Long): PersonInfoResponse {
        var value = PersonInfoResponse(0,"","","","","")
        withContext(Dispatchers.IO){
            try{
                value =  API.RETROFIT_SERVICE.getInfoPeople(id).await()
            }
            catch (e: Exception){
                value = PersonInfoResponse(0,"","","","","")
            }
        }
        return value
    }
}