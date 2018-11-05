package com.example.shogoyamada.mutimodulesample.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shogoyamada.mutimodulesample.Test.TestActivity
import com.example.shogoyamada.mutimodulesample.databinding.MainFragmentBinding
import org.koin.android.architecture.ext.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = MainFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = this@MainFragment.viewModel
        }

        viewModel.navigationTestPage.observeForever {
            startActivity(Intent(requireContext(), TestActivity::class.java))
        }

        viewModel.errorDialog.observeForever {
            it ?: return@observeForever
            showErrorDialog(it)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserInfoSetTimeout()
    }

    private fun showErrorDialog(errorBody: ErrorBody) {
        AlertDialog.Builder(requireContext())
                .setTitle("エラー")
                .setMessage("mainErrorMessage: ${errorBody.mainErrorBody.toString()} contentErrorMessage: ${errorBody.contentErrorBody.toString()}")
                .setPositiveButton("OK", null).show()

        print(errorBody.mainErrorBody)
        print(errorBody.contentErrorBody)
    }
}
