package com.example.shogoyamada.mutimodulesample.ui.main

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.Response

interface MainService {

    companion object {
        const val baseUrl = "https://firebasestorage.googleapis.com/"
    }

    @GET("v0/b/blog-1a47d.appspot.com/o/user.json?alt=media&token=6dfc5fe7-b736-4ea4-bc51-555cca8ba743")
    fun getUser(): Deferred<Response<Main>>
}