package com.example.moviedb.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.model.Series
import com.squareup.picasso.Picasso

class ItemSeriesHolder private constructor(view: View): RecyclerView.ViewHolder(view){
    val imageViewFilm = view.findViewById<ImageView>(R.id.iv_film_item_movies)
    val textViewNameFilm = view.findViewById<TextView>(R.id.tv_name_film_item_movies)

    fun bind(series: Series){
        textViewNameFilm.text = series.name
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+series.poster_path).into(imageViewFilm)
    }

    companion object{
        fun from(parent: ViewGroup): ItemSeriesHolder{
            return ItemSeriesHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_movies, parent, false))
        }
    }
}