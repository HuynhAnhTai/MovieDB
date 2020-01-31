package com.example.moviedb.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviedb.model.Series
import com.example.moviedb.viewHolder.ItemSeriesHolder

class SeriessAdapter(val seriesClick: SeriesClick) : ListAdapter<Series, ItemSeriesHolder>(SeriesDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSeriesHolder {
        return ItemSeriesHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemSeriesHolder, position: Int) {
        var item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            seriesClick.onClick(item)
        }
    }
}

class SeriesDiffCallBack : DiffUtil.ItemCallback<Series>(){
    override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem == newItem
    }

}

class SeriesClick(val clickListener: (name: String) -> Unit){
    fun onClick(item: Series) = clickListener(item.name)
}