package com.example.moviedb.worker

import android.app.Application
import android.content.Context
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.repository.MovieRepository
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.moviedb.MainActivity
import android.app.NotificationChannel
import java.util.*


class UpdateMovieSaveWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    private var moviesRepository = MovieRepository(applicationContext as Application)

    var sharedPref = applicationContext.getSharedPreferences("id",Context.MODE_PRIVATE)
    var movieSave: String? = sharedPref.getString("IdSaveMovie","")
    var arrayId: Array<String> = movieSave!!.split(",").toTypedArray()

    override suspend fun doWork(): Result {
        updateData()
        return Result.success()
    }

    private suspend fun updateData() {
        if (arrayId!=null) {
            for (i in arrayId) {
                var value = moviesRepository.getDetailMovieById(i.toLong())

                var genres: String = ""
                for (g in value.genres) {
                    if (g.equals(value.genres.get(value.genres.size-1))){
                        genres += g.name
                    }else{
                        genres += g.name+", "
                    }
                }

                var entity = MoviesEntity(
                    value.adult, value.backdrop_path, genres, value.id,
                    value.overview, value.poster_path, value.title, value.vote_average
                )
                moviesRepository.insertMovieToDB(entity)
                makeNotify()
            }
        }
    }

    private fun makeNotify(){
        val intent = Intent(applicationContext, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, "default")
            .setContentTitle("Update data")
            .setContentIntent(pendingIntent)
            .setSmallIcon(com.example.moviedb.R.mipmap.ic_launcher)
            .setAutoCancel(true)

        Objects.requireNonNull(notificationManager).notify(0, notification.build())
    }
}



