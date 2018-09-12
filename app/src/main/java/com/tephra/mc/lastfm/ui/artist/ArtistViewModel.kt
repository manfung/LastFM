package com.tephra.mc.lastfm.ui.artist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tephra.mc.lastfm.data.model.ArtistResult
import com.tephra.mc.lastfm.data.repository.ILastFmRepo
import com.tephra.mc.lastfm.data.repository.Resource
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class ArtistViewModel @Inject constructor(private val lastFmRepo: ILastFmRepo): ViewModel() {

    val artist: MutableLiveData<Resource<ArtistResult>> = MutableLiveData()

    fun getArtist(id:String) {

        launch(CommonPool) {
            val response = lastFmRepo.getArtist(id)
            artist.postValue(response)
        }
    }

}
