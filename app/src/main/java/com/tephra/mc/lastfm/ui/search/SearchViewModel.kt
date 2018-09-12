package com.tephra.mc.lastfm.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tephra.mc.lastfm.data.model.Artist
import com.tephra.mc.lastfm.data.model.SearchResults
import com.tephra.mc.lastfm.data.repository.ILastFmRepo
import com.tephra.mc.lastfm.data.repository.Resource
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val lastFmRepo: ILastFmRepo): ViewModel() {

    val initialArtists: MutableLiveData<Resource<SearchResults>> = MutableLiveData()
    private val artistsList = mutableListOf<Artist>()
    val newArtists: MutableLiveData<Resource<SearchResults>> = MutableLiveData()

    private var totalSearchResults = 0
    private var searchItemsPerPage = 0
    private var searchPageNum = 1
    private var search = ""

    fun searchForArtist(search:String) {

        if (search != "") {
            this.search = search
            launch(CommonPool) {
                val response = lastFmRepo.searchForArtist(search)
                with (response.data?.results!!) {
                    totalSearchResults = totalResults
                    searchItemsPerPage = itemsPerPage
                    searchPageNum = startIndex + 1
                    artistsList.clear()
                    artistsList.addAll(artistmatches.artist)
                }

                initialArtists.postValue(response)
            }
        } else {
            initialArtists.postValue(Resource.error("Search can not be empty",null))
        }

    }

    fun getNextPage() {

        // work out if its the last page
        if (searchItemsPerPage * searchPageNum < totalSearchResults) {

            launch(CommonPool) {
                val response = lastFmRepo.searchForArtist(search, ++searchPageNum)
                newArtists.postValue(response)
            }
        }else {
            //  just to notify UI layer
            newArtists.postValue(Resource.success(null))
        }

    }

}
