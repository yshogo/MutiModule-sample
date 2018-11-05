package com.example.shogoyamada.mutimodulesample.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.example.repository.MainRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    var userModel = ObservableField<Main>()
    var contentModel = ObservableField<Content>()

    private val _navgationTestPage = MutableLiveData<String>()
    val navigationTestPage: LiveData<String>
        get() = _navgationTestPage

    private val _errorDialog = MutableLiveData<ErrorBody>()
    val errorDialog: LiveData<ErrorBody>
        get() = _errorDialog

    fun getUserInfoCatchError() {

        val info = repository.getUserInfo()

        GlobalScope.launch {

            val userRequest = info.getUser()
            val contentRequest = info.getContent()
            val userResponse = userRequest.await()
            val contentResponse = contentRequest.await()

            if (!userResponse.isSuccessful || !contentResponse.isSuccessful) {

                _errorDialog.postValue(ErrorBody().apply {
                    mainErrorBody = userResponse.errorBody()
                    mainErrorMessage = userResponse.message()

                    contentErrorMessage = contentResponse.message()
                    contentErrorBody = contentResponse.errorBody()
                })
                return@launch
            }

            userModel.set(userResponse.body() ?: return@launch)
            contentModel.set(contentResponse.body() ?: return@launch)
        }
    }

    fun getUserInfoSetTimeout() {
        val info = repository.getUserInfo()
        GlobalScope.launch {
            withTimeout(300) {

                try {
                    val userRequest = info.getUser()
                    val contentRequest = info.getContent()
                    val userResponse = userRequest.await()
                    val contentResponse = contentRequest.await()

                    if (!userResponse.isSuccessful || !contentResponse.isSuccessful) {

                        _errorDialog.postValue(ErrorBody().apply {
                            mainErrorBody = userResponse.errorBody()
                            mainErrorMessage = userResponse.message()

                            contentErrorMessage = contentResponse.message()
                            contentErrorBody = contentResponse.errorBody()
                        })
                        return@withTimeout
                    }

                    userModel.set(userResponse.body() ?: return@withTimeout)
                    contentModel.set(contentResponse.body() ?: return@withTimeout)
                }catch (e: TimeoutCancellationException) {
                    // エラーをキャッチする
                    print(e)
                }
            }
        }
    }

    fun getUserInfo_retry() {

        GlobalScope.launch {

        }
    }
}
