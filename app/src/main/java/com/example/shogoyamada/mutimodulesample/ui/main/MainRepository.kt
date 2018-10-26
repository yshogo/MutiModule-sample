package com.example.shogoyamada.mutimodulesample.ui.main

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainRepository {

    fun getUserInfo() = Retrofit.Builder()
            .baseUrl(MainService.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(MainService::class.java)
}