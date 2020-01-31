package com.example.moviedb.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviedb.model.Movies
import com.example.moviedb.model.Peoples
import com.example.moviedb.viewHolder.ItemMoviesHolder
import com.example.moviedb.viewHolder.ItemPeoplessHolder

class PeoplesAdapter(val peopleClick: PeoplesClick) : ListAdapter<Peoples, ItemPeoplessHolder>(PeoplesDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPeoplessHolder {
        return ItemPeoplessHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemPeoplessHolder, position: Int) {
        var item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            peopleClick.onClick(item)
        }
    }
}

class PeoplesDiffCallBack : DiffUtil.ItemCallback<Peoples>(){
    override fun areItemsTheSame(oldItem: Peoples, newItem: Peoples): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Peoples, newItem: Peoples): Boolean {
        return oldItem == newItem
    }

}

class PeoplesClick(val clickListener: (name: String) -> Unit){
    fun onClick(item: Peoples) = clickListener(item.name)
}