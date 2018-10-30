package com.example.shogoyamada.mutimodulesample.Test

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.shogoyamada.mutimodulesample.R
import com.example.shogoyamada.mutimodulesample.Test.ui.test.TestFragment

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TestFragment.newInstance())
                    .commitNow()
        }
    }

}
