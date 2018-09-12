package com.tephra.mc.latestnews.data.repository

import com.tephra.mc.lastfm.data.model.ArtistResult
import com.tephra.mc.lastfm.data.model.SearchResults
import com.tephra.mc.lastfm.data.repository.ILastFmRepo
import com.tephra.mc.lastfm.data.repository.Resource
import javax.inject.Inject

class LastFmRepo @Inject constructor(private val apiService: ApiService): ILastFmRepo {

    override suspend fun searchForArtist(artist: String, page: Int): Resource<SearchResults> {
        return try {
            val response = apiService.searchForArtist(artist = artist, page = page)
            val result = response.await()
            Resource.success(result)
        } catch (e: Throwable) {
            Resource.error(e.message!!, data = null)
        }
    }

    override suspend fun getArtist(id: String): Resource<ArtistResult> {
        return try {
            val response = apiService.getArtist(id = id)
            val result = response.await()
            Resource.success(result)
        } catch (e: Throwable) {
            Resource.error(e.message!!, data = null)
        }
    }

}