package com.example.moviedb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MoviesEntity::class],version = 2)
abstract class MoviesDatabase : RoomDatabase(){
    abstract val moviesDao: MoviesDAO
}

private lateinit var INSTANCE: MoviesDatabase
fun getDatabase(context: Context):MoviesDatabase{
    synchronized(MoviesDatabase::class.java){
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java,
                "movies")
                .addMigrations()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE

}