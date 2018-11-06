package com.example.shogoyamada.mutimodulesample.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.example.repository.MainRepository
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Exception

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

    fun getUserInfoEach() {

        val info = repository.getUserInfo()

        GlobalScope.launch {

            val job = launch {
                val userRequest = info.getUser()
                val contentRequest = info.getContent()
                val userResponse = userRequest.await()
                val contentResponse = contentRequest.await()

//            if (!userResponse.isSuccessful || !contentResponse.isSuccessful) {
//
//                _errorDialog.postValue(ErrorBody().apply {
//                    mainErrorBody = userResponse.errorBody()
//                    mainErrorMessage = userResponse.message()
//
//                    contentErrorMessage = contentResponse.message()
//                    contentErrorBody = contentResponse.errorBody()
//                })
//                return@launch
//            }

                joinAll(userRequest, contentRequest)
                userModel.set(userResponse.body() ?: return@launch)
                contentModel.set(contentResponse.body() ?: return@launch)
            }

            job.join()
        }
    }

    fun getUserInfoSetTimeout() {
        val info = repository.getUserInfo()
        val job = GlobalScope.launch {
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
                } catch (e: TimeoutCancellationException) {
                    // エラーをキャッチする
                    print(e)
                }
            }
        }
    }

    fun getUserInfoRetry() {

        val info = repository.getUserInfo()

        GlobalScope.launch {
            retryIO(times = 2) {
                try {
                    val userRequest = info.getUser()
                    val userResponse = userRequest.await()

                    if (userResponse.isSuccessful) {
                        userModel.set(userResponse.body() ?: return@retryIO)
                    }

                }catch (e: Exception) {

                    print(e)
                }
            }
        }
    }

    suspend fun <T> retryIO(
            times: Int = Int.MAX_VALUE,
            initialDelay: Long = 100, // 0.1 second
            maxDelay: Long = 1000,    // 1 second
            factor: Double = 2.0,
            block: suspend () -> T): T {

        var currentDelay = initialDelay
        repeat(times - 1) {
            try {
                return block()
            } catch (e: IOException) {
                // ここでリトライするかどうか判定する
                // 例えば、ネットワークタイムアウトだったらリトライ、エラーコードが
                // 返ってきてたらエラーのするとか
                block()
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
        return block() // last attempt
    }
}
