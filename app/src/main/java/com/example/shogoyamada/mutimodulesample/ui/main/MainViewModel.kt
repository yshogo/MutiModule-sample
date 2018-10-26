package com.example.shogoyamada.mutimodulesample.ui.main

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import kotlinx.coroutines.experimental.launch

class MainViewModel : ViewModel() {

    var userModel = ObservableField<Main>()

    fun getUserInfo() {

        val repository = MainRepository().getUserInfo()

        launch {
            val request = repository.getUser()
            val response = request.await()
            if (response.isSuccessful) {
                val model = response.body() ?: return@launch
                userModel.set(model)
            } else {
                print(response.errorBody().toString())
            }
        }
    }
}
