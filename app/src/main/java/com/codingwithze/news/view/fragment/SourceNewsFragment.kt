package com.codingwithze.news.view.fragment

import android.annotation.SuppressLint
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
import com.codingwithze.news.databinding.FragmentSourceNewsBinding
import com.codingwithze.news.model.article.Article
import com.codingwithze.news.model.source.Source
import com.codingwithze.news.view.adapter.ArticleAdapter
import com.codingwithze.news.view.adapter.SourceAdapter
import com.codingwithze.news.viewmodel.ViewModelArticle
import com.codingwithze.news.viewmodel.ViewModelSource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class SourceNewsFragment : Fragment() {


    lateinit var binding : FragmentSourceNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSourceNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getName = arguments?.getString("name")
        Toast.makeText(context, "News Category : $getName", Toast.LENGTH_SHORT).show()

        binding.btnSearchSource.setOnClickListener{
            getSearchSource(getName.toString())
        }

        val viewModel = ViewModelProvider(this).get(ViewModelSource::class.java)
        viewModel.callApiSource(getName.toString(),"",requireContext())
        viewModel.getDataSource().observe(viewLifecycleOwner){
            if (it != null){
                showSource(it)
            }
        }
    }

    fun showSource(data : List<Source>){
        val adapter = SourceAdapter(data)
        binding.rvSource.adapter = adapter
        binding.rvSource.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.onClick = {
            val sourc = it.id
                val bund = Bundle()
                bund.putString("name", sourc)
            Navigation.findNavController(requireView()).navigate(R.id.action_sourceNewsFragment_to_articleFragment,bund)
        }
    }

    fun getSearchSource(category : String){
        if (binding.searchSource.text.toString().isNotEmpty()){
            val viewModel = ViewModelProvider(this).get(ViewModelSource::class.java)
            viewModel.callApiSource("",binding.searchSource.text.toString(), requireContext())
            viewModel.getDataSource().observe(viewLifecycleOwner){
                if (it != null){
                    showSource(it)
                }
            }
        } else {
            val viewModel = ViewModelProvider(this).get(ViewModelSource::class.java)
            viewModel.callApiSource(category,"", requireContext())
            viewModel.getDataSource().observe(viewLifecycleOwner){
                if (it != null){
                    showSource(it)
                }
            }
        }
    }
}