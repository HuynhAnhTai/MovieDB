package com.example.moviedb.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.viewHolder.ItemSaveMoviesHolder

class SaveMovieAdapter(val saveMoviesClick: SaveMoviesClick) :ListAdapter<MoviesEntity,ItemSaveMoviesHolder>(SaveMoviesDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSaveMoviesHolder {
        return ItemSaveMoviesHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemSaveMoviesHolder, position: Int) {
        var item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            saveMoviesClick.onClick(item)
        }
    }
}

class SaveMoviesDiffCallBack : DiffUtil.ItemCallback<MoviesEntity>(){
    override fun areItemsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
        return oldItem == newItem
    }

}

class SaveMoviesClick(val clickListener: (id: Long) -> Unit){
    fun onClick(item: MoviesEntity) = clickListener(item.id)
}