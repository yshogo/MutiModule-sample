package com.example.repository

import com.example.shogoyamada.mutimodulesample.ui.main.Content
import com.example.shogoyamada.mutimodulesample.ui.main.Main
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.Response

interface MainService {

    companion object {
        const val baseUrl = "https://firebasestorage.googleapis.com/"
    }

    @GET("v0/b/blog-1a47d.appspot.com/o/user.json?alt=media&token=6dfc5fe7-b736-4ea4-bc51-555cca8ba743")
    suspend fun getUser(): Deferred<Response<Main>>

    @GET("v0/b/blog-1a47d.appspot.com/o/json%2Fcontent.json?alt=media&token=57ebead6-c44d-4650-a036-48bf6d067aa1")
    suspend fun getContent(): Deferred<Response<Content>>
}