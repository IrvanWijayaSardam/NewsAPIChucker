package com.codingwithze.news.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codingwithze.news.databinding.ItemArticleBinding
import com.codingwithze.news.model.article.Article

class ArticleAdapter(var listArticle : List<Article>): RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    var onClick : ((Article) -> Unit)? = null
    class ViewHolder(var binding : ItemArticleBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var source = listArticle[position]
        holder.binding.articleTitle.text = source.title
        Glide.with(holder.itemView).load(source.urlToImage).into(holder.binding.articleImage)
        holder.binding.cardArticle.setOnClickListener {
            Log.d("cat", source.url)
            Toast.makeText(holder.itemView.context, source.url, Toast.LENGTH_SHORT).show()
            onClick?.invoke(source)
        }
    }

    override fun getItemCount(): Int {
        return listArticle.size
    }
}