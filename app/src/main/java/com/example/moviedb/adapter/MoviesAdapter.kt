package com.example.moviedb.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviedb.model.MoviesTopRatedResults
import com.example.moviedb.viewHolder.ItemMoviesHolder

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