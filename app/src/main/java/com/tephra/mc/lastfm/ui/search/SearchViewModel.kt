package com.tephra.mc.lastfm.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tephra.mc.lastfm.data.model.SearchResults
import com.tephra.mc.lastfm.data.repository.ILastFmRepo
import com.tephra.mc.lastfm.data.repository.Resource
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val lastFmRepo: ILastFmRepo): ViewModel() {

    val artists: MutableLiveData<Resource<SearchResults>> = MutableLiveData()

    private var totalSearchResults = 0
    private var searchItemsPerPage = 0
    private var searchPageNum = 0

    fun searchByArtist(search:String) {

        launch(CommonPool) {
            val response = lastFmRepo.searchByArtist(search)
            with (response?.data?.results!!) {
                totalSearchResults = totalResults
                searchItemsPerPage = itemsPerPage
                searchPageNum = startIndex
            }

            artists.postValue(response)
        }
    }


}
