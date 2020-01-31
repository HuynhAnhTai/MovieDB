package com.example.moviedb.viewHolder

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.model.Movies
import com.example.moviedb.model.MoviesTopRatedResponse
import com.squareup.picasso.Picasso

class ItemMoviesHolder private constructor(view: View): RecyclerView.ViewHolder(view){
    val imageViewFilm = view.findViewById<ImageView>(R.id.iv_film_item_movies)
    val textViewNameFilm = view.findViewById<TextView>(R.id.tv_name_film_item_movies)

    fun bind(movies: Movies){
        textViewNameFilm.text = movies.title
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+movies.poster_path).into(imageViewFilm)
    }

    companion object{
        fun from(parent: ViewGroup): ItemMoviesHolder{
            return ItemMoviesHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_movies, parent, false))
        }
    }
}