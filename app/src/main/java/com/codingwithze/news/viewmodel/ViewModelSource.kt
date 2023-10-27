package com.codingwithze.news.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingwithze.news.MainActivity
import com.codingwithze.news.NewsApplication
import com.codingwithze.news.api.ApiClient
import com.codingwithze.news.api.ApiService
import com.codingwithze.news.model.source.ResponseSource
import com.codingwithze.news.model.source.Source
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class ViewModelSource @Inject constructor(var api : ApiService) : ViewModel(){

    lateinit var liveDataSource : MutableLiveData<List<Source>?>

    init {
        liveDataSource = MutableLiveData()
    }

    fun getDataSource(): MutableLiveData<List<Source>?> {
        return  liveDataSource
    }

    fun callApiSource(categ : String,query: String, context: Context){
        api.getAllSources(categ,query)
            .enqueue(object : Callback<ResponseSource>{
                override fun onResponse(
                    call: Call<ResponseSource>,
                    response: Response<ResponseSource>
                ) {
                    if (response.isSuccessful){
                            liveDataSource.postValue(response.body()!!.sources)
                            Log.d(TAG, "onResponse: ${response.body()!!.sources.size}")
                    }else{
                        Log.d(TAG, "onResponse: Unsuccessfully")
                        liveDataSource.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseSource>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                    liveDataSource.postValue(null)
                }

            })
    }


}