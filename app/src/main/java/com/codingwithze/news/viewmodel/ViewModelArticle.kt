package com.codingwithze.news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingwithze.news.api.ApiClient
import com.codingwithze.news.api.ApiService
import com.codingwithze.news.model.article.Article
import com.codingwithze.news.model.article.ResponseArticles
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelArticle @Inject constructor(var api : ApiService): ViewModel() {

    lateinit var liveDataArticle : MutableLiveData<List<Article>?>

    init {
        liveDataArticle = MutableLiveData()
    }

    fun getDataArticle(): MutableLiveData<List<Article>?> {
        return  liveDataArticle
    }



    fun callApiArticle(source : String){
        api.getAllArticles(source)
            .enqueue(object : Callback<ResponseArticles>{
                override fun onResponse(
                    call: Call<ResponseArticles>,
                    response: Response<ResponseArticles>
                ) {
                    if (response.isSuccessful){
                        liveDataArticle.postValue(response.body()!!.articles)
                    }else{
                        liveDataArticle.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseArticles>, t: Throwable) {
                    liveDataArticle.postValue(null)
                }

            })
    }

    fun searchApiArticle(search : String){
        api.searchNews(search)
            .enqueue(object : Callback<ResponseArticles>{
                override fun onResponse(
                    call: Call<ResponseArticles>,
                    response: Response<ResponseArticles>
                ) {
                    if (response.isSuccessful){
                        liveDataArticle.postValue(response.body()!!.articles)
                    }else{
                        liveDataArticle.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseArticles>, t: Throwable) {
                    liveDataArticle.postValue(null)
                }

            })
    }
}