package com.example.audiorecorder.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.Database
import com.example.audiorecorder.api.ApiService
import com.example.audiorecorder.model.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.IOException

class GithubRepository(private val service:ApiService,private val database: Database) {

    private val inMemoryCache = mutableListOf<Repo>()

    private val searchResults = MutableSharedFlow<List<Repo>>(replay = 1)


}