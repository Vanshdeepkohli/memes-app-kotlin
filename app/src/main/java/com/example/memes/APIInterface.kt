package com.example.memes

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("/gimme")
    fun getData() : Call<responseDataClass>
}