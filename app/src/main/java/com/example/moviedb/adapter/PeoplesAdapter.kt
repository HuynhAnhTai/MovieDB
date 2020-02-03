package com.example.moviedb.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviedb.modelAPI.PeoplesPopularResults
import com.example.moviedb.viewHolder.ItemPeoplesHolder

class PeoplesAdapter(val peopleClick: PeoplesClick) : ListAdapter<PeoplesPopularResults, ItemPeoplesHolder>(PeoplesDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPeoplesHolder {
        return ItemPeoplesHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemPeoplesHolder, position: Int) {
        var item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            peopleClick.onClick(item)
        }
    }
}

class PeoplesDiffCallBack : DiffUtil.ItemCallback<PeoplesPopularResults>(){
    override fun areItemsTheSame(oldItem: PeoplesPopularResults, newItem: PeoplesPopularResults): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PeoplesPopularResults, newItem: PeoplesPopularResults): Boolean {
        return oldItem == newItem
    }

}

class PeoplesClick(val clickListener: (id: Long) -> Unit){
    fun onClick(item: PeoplesPopularResults) = clickListener(item.id)
}