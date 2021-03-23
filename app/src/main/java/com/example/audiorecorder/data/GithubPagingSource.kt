package com.example.audiorecorder.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.audiorecorder.api.ApiService
import com.example.audiorecorder.model.Repo
import retrofit2.HttpException
import java.io.IOException

class GithubPagingSource( private val service: ApiService, private var query:String):PagingSource<Int,Repo>() {
    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key?:1
        val apiQuery = query+"in:name,description"
        return try {
            val response  = service.searchRepos(query,position,params.loadSize)
            val repos = response.items
            val nextKey = if(repos.isEmpty()){
                null
            }else{
                val NETWORK_PAGE_SIZE = 50
                position+(params.loadSize / NETWORK_PAGE_SIZE)
            }

            LoadResult.Page(
                data = repos,
                prevKey = if(position == 1) null else position-1,
                nextKey = nextKey
            )
        }catch (e:IOException){
            return LoadResult.Error(e)
        }catch (e:HttpException){
            return LoadResult.Error(e)
        }
    }
}