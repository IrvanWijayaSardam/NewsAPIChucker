package com.codingwithze.news.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.codingwithze.news.databinding.ItemCategoryBinding
import com.codingwithze.news.databinding.ItemSourceBinding
import com.codingwithze.news.model.CategoryData
import com.codingwithze.news.model.source.Source

class SourceAdapter(var listSource : List<Source>):RecyclerView.Adapter<SourceAdapter.ViewHolder>() {

    var onClick : ((Source) -> Unit)? = null


    class ViewHolder(var binding : ItemSourceBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var source = listSource[position]
        holder.binding.nameSource.text = source.name
        holder.binding.itemSource.setOnClickListener {
            onClick?.invoke(source)
        }
    }

    override fun getItemCount(): Int {
         return  listSource.size
    }
}