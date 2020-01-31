package com.example.moviedb.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.model.Peoples
import com.squareup.picasso.Picasso

class ItemPeoplessHolder private constructor(view: View): RecyclerView.ViewHolder(view){
    val imageViewFilm = view.findViewById<ImageView>(R.id.iv_film_item_movies)
    val textViewNameFilm = view.findViewById<TextView>(R.id.tv_name_film_item_movies)

    fun bind(peoples: Peoples){
        textViewNameFilm.text = peoples.name
        if (peoples.profile_path == null){
            Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuErL5FJbhFsb_E5fB7HI5uxuDn3EaxiJfDXxeqjZW" +
                    "CMSgwGJ7&s").into(imageViewFilm)
        }else{

            Picasso.get().load("https://image.tmdb.org/t/p/w500"+peoples.profile_path).into(imageViewFilm)
        }

    }

    companion object{
        fun from(parent: ViewGroup): ItemPeoplessHolder{
            return ItemPeoplessHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_movies, parent, false))
        }
    }
}