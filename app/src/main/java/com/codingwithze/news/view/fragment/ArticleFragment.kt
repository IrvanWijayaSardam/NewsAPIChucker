package com.codingwithze.news.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingwithze.news.R
import com.codingwithze.news.databinding.FragmentArticleBinding
import com.codingwithze.news.model.article.Article
import com.codingwithze.news.view.adapter.ArticleAdapter
import com.codingwithze.news.viewmodel.ViewModelArticle
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    lateinit var binding : FragmentArticleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getName = arguments?.getString("name")
        Toast.makeText(context, "Article : $getName", Toast.LENGTH_SHORT).show()
        showDataArticle(getName.toString())

        getSearchArticle()
        binding.btnSearch.setOnClickListener {
            getSearchArticle()
        }

    }



    fun showDataArticle(source : String){
        val viewModel = ViewModelProvider(this).get(ViewModelArticle::class.java)
        viewModel.callApiArticle(source)
        viewModel.getDataArticle().observe(viewLifecycleOwner){
            if (it != null){
                showArticle(it)
            }
        }
    }

    fun showArticle(data : List<Article>){
        val adapterArticle = ArticleAdapter(data)
        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = adapterArticle
        }
        adapterArticle.onClick = {
            val detailArc = it.url
            val bund = Bundle()
            bund.putString("url", detailArc)
            Navigation.findNavController(requireView()).navigate(R.id.action_articleFragment_to_detailArticleFragment,bund)
        }
    }

    fun getSearchArticle(){
            if (binding.searchHere.text.toString().isNotEmpty()){
                val viewModel = ViewModelProvider(this).get(ViewModelArticle::class.java)
                viewModel.searchApiArticle(binding.searchHere.text.toString())
                viewModel.getDataArticle().observe(viewLifecycleOwner){
                    if (it != null){
                        showArticle(it)
                    }
                }
            }
    }



}