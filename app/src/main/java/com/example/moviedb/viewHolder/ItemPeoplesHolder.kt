package com.example.moviedb.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.modelAPI.PeoplesPopularResults
import com.squareup.picasso.Picasso

class ItemPeoplesHolder private constructor(view: View): RecyclerView.ViewHolder(view){
    val imageViewFilm = view.findViewById<ImageView>(R.id.iv_film_item_movies)
    val textViewNameFilm = view.findViewById<TextView>(R.id.tv_name_film_item_movies)

    fun bind(peoplesPopularResults: PeoplesPopularResults){
        textViewNameFilm.text = peoplesPopularResults.name
        if (peoplesPopularResults.profile_path == null){
            Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuErL5FJbhFsb_E5fB7HI5uxuDn3EaxiJfDXxeqjZW" +
                    "CMSgwGJ7&s").into(imageViewFilm)
        }else{

            Picasso.get().load("https://image.tmdb.org/t/p/w500"+peoplesPopularResults.profile_path).into(imageViewFilm)
        }

    }

    companion object{
        fun from(parent: ViewGroup): ItemPeoplesHolder{
            return ItemPeoplesHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_movies_grid, parent, false))
        }
    }
}