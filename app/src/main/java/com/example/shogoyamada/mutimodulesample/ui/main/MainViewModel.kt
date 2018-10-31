package com.example.shogoyamada.mutimodulesample.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var userModel = ObservableField<Main>()
    var contentModel = ObservableField<Content>()

    private val _navgationTestPage = MutableLiveData<String>()
    val navigationTestPage: LiveData<String>
        get() = _navgationTestPage

    fun getUserInfo() {

        val repository = MainRepository().getUserInfo()

        GlobalScope.launch {
            val userRequest = repository.getUser()
            val userResponse = userRequest.await()

            val contentRequest = repository.getContent()
            val contentResponse = contentRequest.await()

            if (!userResponse.isSuccessful || !contentResponse.isSuccessful) {
                // TODO たぶんここでキャンセル処理を実行しないといけない
                return@launch
            }

            userModel.set(userResponse.body() ?: return@launch)
            contentModel.set(contentResponse.body() ?: return@launch)
        }
    }


    fun onTapButton() {
        _navgationTestPage.value = "test"
    }
}
