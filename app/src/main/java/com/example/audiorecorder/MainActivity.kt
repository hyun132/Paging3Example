package com.example.audiorecorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import com.example.audiorecorder.api.ApiService
import com.example.audiorecorder.databinding.ActivityMainBinding
import com.example.audiorecorder.ui.RepoViewModel
import com.example.audiorecorder.ui.RepoViewModelFactory
import com.example.audiorecorder.ui.ReposAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RepoViewModel
    private val adapter = ReposAdapter()
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(
            this,
            RepoViewModelFactory(ApiService.getApiService())
        )[RepoViewModel::class.java]

        lifecycleScope.launch {
            viewModel.repoList.collect {
                adapter.submitData(it)
            }
        }

        binding.list.adapter=adapter
        binding.list.layoutManager=LinearLayoutManager(this)

        binding.searchRepo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.toString() != "") {

                    binding.searchRepo.text.trim().let {
                        if (it.isNotEmpty()) {
                            search(it.toString())
                        }
                    }
                }
            }

        })
    }


    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepo(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

//    private fun updateRepoListFromInput() {
//
//    }


}