package com.example.moviedb.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.modelAPI.MoviesTopRatedResults
import com.squareup.picasso.Picasso

class ItemMoviesHolder private constructor(view: View, type: Int): RecyclerView.ViewHolder(view){

    private lateinit var imageViewFilm: ImageView
    private lateinit var textViewNameFilm: TextView
    private lateinit var textViewOverview: TextView
    init {
        bindId(type, view)
    }
    fun bindId(typeRelative: Int, view: View){
        if (typeRelative == 3){
            imageViewFilm = view.findViewById<ImageView>(R.id.iv_film_item_movies)
            textViewNameFilm = view.findViewById<TextView>(R.id.tv_name_film_item_movies)
        }else{
            imageViewFilm = view.findViewById<ImageView>(R.id.iv_film_item_movies_list)
            textViewNameFilm = view.findViewById<TextView>(R.id.tv_name_film_item_movies_list)
            textViewOverview = view.findViewById(R.id.tv_overview_item_movie_list)
        }

    }

    fun bind(moviesTopRatedResults: MoviesTopRatedResults, type: Int){
        textViewNameFilm.text = moviesTopRatedResults.title
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+moviesTopRatedResults.poster_path).fit().into(imageViewFilm)
        if (type == 1){
            textViewOverview.text = moviesTopRatedResults.overview
        }
    }

    companion object{
        fun from(parent: ViewGroup, type: Int): ItemMoviesHolder{
            if (type == 3) {
                return ItemMoviesHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.items_movies_grid,
                        parent,
                        false
                    ),type
                )
            }else{
                return ItemMoviesHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_movies_list,
                        parent,
                        false
                    ),type
                )
            }
        }
    }
}