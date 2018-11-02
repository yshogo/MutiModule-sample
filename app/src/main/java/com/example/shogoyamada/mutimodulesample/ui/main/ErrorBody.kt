package com.example.shogoyamada.mutimodulesample.ui.main

import okhttp3.ResponseBody

class ErrorBody {

    var mainErrorMessage: String = ""
    var contentErrorMessage: String = ""

    var mainErrorBody: ResponseBody? = null
    var contentErrorBody: ResponseBody? = null
}