package com.example.rma_app.networking

import com.example.rma_app.model.Show
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/show")
    fun fetchAllShows(): Call<List<Show>>
}