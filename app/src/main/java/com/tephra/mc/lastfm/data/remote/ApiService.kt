package com.tephra.mc.latestnews.data.repository


import com.tephra.mc.lastfm.BuildConfig
import com.tephra.mc.lastfm.data.model.SearchResults
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/2.0")
    fun searchByArtist(@Query("api_key") api: String = BuildConfig.API_KEY,
                       @Query("method") method:String = "artist.search",
                       @Query("format") format: String = "json",
                       @Query("artist") artist: String,
                       @Query("page") page: String = "1")
            : Deferred<SearchResults>

}