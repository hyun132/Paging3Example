package com.example.audiorecorder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.audiorecorder.api.ApiService
import com.example.audiorecorder.data.GithubPagingSource
import com.example.audiorecorder.model.Repo
import kotlinx.coroutines.flow.Flow

class RepoViewModel(private val service: ApiService) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    var repoList = Pager(PagingConfig(pageSize = 50)) {
        GithubPagingSource(service, "glide")
    }.flow.cachedIn(viewModelScope)

    fun searchRepo(query: String): Flow<PagingData<Repo>> {
        repoList = Pager(PagingConfig(pageSize = 50)) {
            GithubPagingSource(service, query)
        }.flow.cachedIn(viewModelScope)
        return repoList
    }

}