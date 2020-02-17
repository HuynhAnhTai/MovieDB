package com.example.moviedb.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.modelAPI.CastOfFlim
import com.example.moviedb.transferShape.CircleTransform
import com.squareup.picasso.Picasso

class ItemCastMovieHolder private constructor(view: View): RecyclerView.ViewHolder(view){
    val imageActor = view.findViewById<ImageView>(R.id.iv_cast_item_detail_moive)
    val textViewNameFilm = view.findViewById<TextView>(R.id.tv_name_cast_item_detail_movie)

    fun bind(castOfFlim: CastOfFlim){
        textViewNameFilm.text = castOfFlim.name
        if (castOfFlim.profile_path == null){
            Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuErL5FJbhFsb_E5fB7HI5uxuDn3EaxiJfDXxeqjZW" +
                    "CMSgwGJ7&s").transform(CircleTransform())
                .fit()
                .centerInside()
                .into(imageActor)
        }else {
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + castOfFlim.profile_path)
                .transform(CircleTransform())
                .fit()
                .centerInside()
                .into(imageActor)
        }
    }

    companion object{
        fun from(parent: ViewGroup): ItemCastMovieHolder{
            return ItemCastMovieHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cast_movie, parent, false))
        }
    }
}