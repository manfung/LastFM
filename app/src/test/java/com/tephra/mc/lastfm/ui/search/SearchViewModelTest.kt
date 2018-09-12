package com.tephra.mc.lastfm.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.tephra.mc.lastfm.data.model.ArtistSearchResults
import com.tephra.mc.lastfm.data.model.Artists
import com.tephra.mc.lastfm.data.model.SearchResults
import com.tephra.mc.lastfm.data.repository.ILastFmRepo
import com.tephra.mc.lastfm.data.repository.Resource
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class SearchViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var searchViewModel: SearchViewModel
    private val lastFmRepo = mock<ILastFmRepo>()

    @Before
    fun setup() {
        searchViewModel = SearchViewModel(lastFmRepo)
    }

    @Test
    fun testSearchForArtist() {

        // create a success response
        val artists = Artists(listOf())
        val artistSearchResults = ArtistSearchResults(artists)
        val searchResults = SearchResults(artistSearchResults)
        val resource = Resource.success(searchResults)

        // create mock Observer class and add it as view models LiveData observer
        val observer = mock<Observer<Resource<SearchResults>>>()
        searchViewModel.initialArtists.observeForever(observer)

        // mock the lastFmRepository response to the success object defined earlier
        Mockito.`when`(runBlocking { lastFmRepo.searchForArtist("test") }).thenReturn(Resource.success(searchResults))
        searchViewModel.searchForArtist("test")

        var liveData =  MutableLiveData<Resource<SearchResults>> ()
        liveData.value = resource

        // verify on observer object if transformation in view model matches the success response
        verify(observer).onChanged(resource)

    }

}