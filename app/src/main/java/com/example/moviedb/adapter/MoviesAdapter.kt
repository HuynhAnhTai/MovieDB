package com.example.moviedb.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviedb.model.Movies
import com.example.moviedb.viewHolder.ItemMoviesHolder

class MoviesAdapter(val filmClick: MoviesClick) : ListAdapter<Movies,ItemMoviesHolder>(MoviesDiffCallBack()){
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

class MoviesDiffCallBack : DiffUtil.ItemCallback<Movies>(){
    override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem == newItem
    }

}

class MoviesClick(val clickListener: (name: String) -> Unit){
    fun onClick(item: Movies) = clickListener(item.name)
}