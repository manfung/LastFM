package com.tephra.mc.latestnews.data.repository

import com.tephra.mc.lastfm.data.model.SearchResults
import com.tephra.mc.lastfm.data.repository.ILastFmRepo
import com.tephra.mc.lastfm.data.repository.Resource
import javax.inject.Inject

class LastFmRepo @Inject constructor(private val apiService: ApiService): ILastFmRepo {

    override suspend fun searchByArtist(artist: String): Resource<SearchResults> {
        return try {
            val response = apiService.searchByArtist(artist = artist)
            val result = response.await()
            Resource.success(result)
        } catch (e: Throwable) {
            Resource.error(e.message!!, data = null)
        }
    }


}