package com.tephra.mc.lastfm.ui.artist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tephra.mc.lastfm.R
import com.tephra.mc.lastfm.data.model.Artist
import com.tephra.mc.lastfm.data.model.ArtistResult
import com.tephra.mc.lastfm.data.repository.Resource
import com.tephra.mc.lastfm.data.repository.Status
import com.tephra.mc.lastfm.extension.htmlText
import com.tephra.mc.lastfm.extension.loadFromUrl
import com.tephra.mc.lastfm.shared.Constants.Companion.INTENT_ID_KEY
import com.tephra.mc.lastfm.shared.Constants.Companion.INTENT_IMAGE_URL_KEY
import com.tephra.mc.lastfm.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_artist.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch

class ArtistActivity : BaseActivity() {

    companion object {
        const val NO_VALUE = ""
    }

    private lateinit var artistViewModel: ArtistViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)

        setupViewModel(intent.getStringExtra(INTENT_ID_KEY))
        initViews(intent.getStringExtra(INTENT_IMAGE_URL_KEY))
    }

    private fun setupViewModel(id: String) {

        artistViewModel = ViewModelProviders.of(this, viewModelFactory)[ArtistViewModel::class.java]

        if (id != NO_VALUE) {

            artistViewModel.artist.observe(this, Observer<Resource<ArtistResult>> {
                stateChanged(it!!)

            })

            artistViewModel.getArtist(id)


        } else{
            showError()
            progress_bar.visibility = View.GONE
        }

    }

    private fun initViews(url: String) {

        iv_image.loadFromUrl(url)
    }

    private fun stateChanged(resource: Resource<ArtistResult>) {

        when (resource.status) {
            Status.SUCCESS -> {
                updateUI(resource.data!!.artist)
            }
            Status.ERROR -> showError(resource.message!!)
        }
        progress_bar.visibility = View.GONE
    }

    private fun updateUI(artist: Artist) {

        with (artist) {

            tv_name.text = name
            tv_summary.htmlText(bio.summary)
            tv_content.htmlText((bio.content))


        }
    }

}
