package com.example.shogoyamada.mutimodulesample

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.shogoyamada.mutimodulesample.ui.main.MainRepository
import com.example.shogoyamada.mutimodulesample.ui.main.MainViewModel


class MainViewModelFactory(val repository: MainRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(this.repository) as T
    }
}