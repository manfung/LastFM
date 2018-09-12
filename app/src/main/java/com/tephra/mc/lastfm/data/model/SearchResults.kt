package com.tephra.mc.lastfm.data.model

import com.google.gson.annotations.SerializedName

data class SearchResults(val results: ArtistSearchResults)

data class ArtistSearchResults(val artistmatches: Artists,
                               @SerializedName("opensearch:totalResults") val totalResults: Int = 0,
                               @SerializedName("opensearch:itemsPerPage") val itemsPerPage: Int = 0,
                               @SerializedName("opensearch:startIndex") val startIndex: Int = 0
                               )

data class Artists(val artist: List<Artist>)