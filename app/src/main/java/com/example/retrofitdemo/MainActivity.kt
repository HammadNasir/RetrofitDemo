package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retService = RetrofitInstance.getRetrofitInstance().create(AlbumsService::class.java)

        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retService.getAlbums()

            //query parameter
            //val response = retService.getSortedAlbums(2)

            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator()
            if (albumsList!=null){
                while (albumsList.hasNext()) {
                    val albumsItem = albumsList.next()
                    Log.d("MyTAG", albumsItem.title)
                }
            }
        })

        // path parameter
        val pathResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.getAlbum(5)
            emit(response)
        }
        pathResponse.observe(this, Observer{
            val title = it.body()?.title
            Log.d("MyTAG", title.toString())
        })
    }
}