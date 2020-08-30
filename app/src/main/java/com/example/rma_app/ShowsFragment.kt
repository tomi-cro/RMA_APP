package com.example.rma_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rma_app.adapters.ShowsAdapter
import com.example.rma_app.model.Show
import kotlinx.android.synthetic.main.fragment_shows.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShowsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) : View? {
        val root = inflater.inflate(R.layout.fragment_shows, container, false)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.tvmaze.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.fetchAllShows().enqueue(object : Callback<List<Show>> {
            override fun onResponse(call: Call<List<Show>>, response: Response<List<Show>>) {
                Log.d("test", "onResponse " + response.body()!![1])
                showData(response.body()!!)
            }

            override fun onFailure(call: Call<List<Show>>, t: Throwable) {
                Log.d("test", "onFailure" + t.message)
            }
        })

        return root
    }

    fun showData(shows: List<Show>){
        recViewShows.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = ShowsAdapter(shows)
        }
    }
}