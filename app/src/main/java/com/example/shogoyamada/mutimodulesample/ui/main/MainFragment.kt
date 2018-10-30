package com.example.shogoyamada.mutimodulesample.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shogoyamada.mutimodulesample.Test.TestActivity
import com.example.shogoyamada.mutimodulesample.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = MainFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = this@MainFragment.viewModel
        }

        viewModel.navigationTestPage.observeForever {
            startActivity(Intent(requireContext(), TestActivity::class.java))
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserInfo()
    }
}
