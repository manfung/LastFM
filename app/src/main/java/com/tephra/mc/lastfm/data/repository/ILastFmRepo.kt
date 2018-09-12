package com.tephra.mc.lastfm.data.repository

import com.tephra.mc.lastfm.data.model.ArtistResult
import com.tephra.mc.lastfm.data.model.SearchResults

interface ILastFmRepo {

    suspend fun searchByArtist(artist: String, page: Int = 1): Resource<SearchResults>

    suspend fun getArtist(id: String): Resource<ArtistResult>

}