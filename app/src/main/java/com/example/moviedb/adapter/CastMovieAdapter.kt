package com.example.moviedb.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviedb.model.CastOfFlim
import com.example.moviedb.viewHolder.ItemCastMovieHolder

class CastMovieAdapter(val castClick: CasterClick) :ListAdapter<CastOfFlim,ItemCastMovieHolder>(CastDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCastMovieHolder {
        return ItemCastMovieHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemCastMovieHolder, position: Int) {
        var item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            castClick.onClick(item)
        }
    }
}

class CastDiffCallBack : DiffUtil.ItemCallback<CastOfFlim>(){
    override fun areItemsTheSame(oldItem: CastOfFlim, newItem: CastOfFlim): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CastOfFlim, newItem: CastOfFlim): Boolean {
        return oldItem == newItem
    }

}

class CasterClick(val clickListener: (id: Long) -> Unit){
    fun onClick(item: CastOfFlim) = clickListener(item.id)
}