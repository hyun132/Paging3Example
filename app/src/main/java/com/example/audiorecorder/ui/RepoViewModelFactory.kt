package com.example.audiorecorder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.audiorecorder.api.ApiService
import java.lang.IllegalArgumentException

class RepoViewModelFactory(private val service: ApiService
) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RepoViewModel::class.java)){
            return RepoViewModel(service) as T
        }
        throw IllegalArgumentException("no fount view model")
    }
}