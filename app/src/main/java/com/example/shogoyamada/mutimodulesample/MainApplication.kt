package com.example.shogoyamada.mutimodulesample

import android.app.Application
import com.example.shogoyamada.mutimodulesample.ui.main.MainRepository
import com.example.shogoyamada.mutimodulesample.ui.main.MainViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.applicationContext

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(module))
    }
}

var module = applicationContext {
    viewModel { MainViewModel(get()) }
    factory { MainViewModelFactory(get()) }
    factory { MainRepository() }
}