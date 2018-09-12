package com.tephra.mc.lastfm.data.repository

import com.tephra.mc.lastfm.data.model.SearchResults

interface ILastFmRepo {

    suspend fun searchByArtist(artist: String): Resource<SearchResults>
}