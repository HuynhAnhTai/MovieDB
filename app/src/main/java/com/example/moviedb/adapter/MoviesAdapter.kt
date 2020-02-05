package com.example.moviedb.adapter

import android.content.Context
import android.icu.util.LocaleData
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviedb.db.FilterEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.modelAPI.MoviesTopRatedResults
import com.example.moviedb.moviesScreen.MoviesFragment
import com.example.moviedb.viewHolder.ItemMoviesHolder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MoviesAdapter(val filmClick: MoviesClick) :ListAdapter<MoviesTopRatedResults,ItemMoviesHolder>(MoviesDiffCallBack()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMoviesHolder {

        return ItemMoviesHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemMoviesHolder, position: Int) {
        var item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            filmClick.onClick(item)
        }
    }
}

class MoviesDiffCallBack : DiffUtil.ItemCallback<MoviesTopRatedResults>(){
    override fun areItemsTheSame(oldItem: MoviesTopRatedResults, newItem: MoviesTopRatedResults): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoviesTopRatedResults, newItem: MoviesTopRatedResults): Boolean {
        return oldItem == newItem
    }

}

class MoviesClick(val clickListener: (id: Long) -> Unit){
    fun onClick(item: MoviesTopRatedResults) = clickListener(item.id)
}