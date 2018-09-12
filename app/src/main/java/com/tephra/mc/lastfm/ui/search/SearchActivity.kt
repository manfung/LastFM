package com.tephra.mc.lastfm.ui.search

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.news.mc.com.newsapp.ui.adapter.SearchListAdapter
import com.tephra.mc.lastfm.R
import com.tephra.mc.lastfm.data.model.Artist
import com.tephra.mc.lastfm.data.model.SearchResults
import com.tephra.mc.lastfm.data.repository.Resource
import com.tephra.mc.lastfm.data.repository.Status
import com.tephra.mc.lastfm.shared.Constants.Companion.INTENT_ID_KEY
import com.tephra.mc.lastfm.shared.Constants.Companion.INTENT_IMAGE_URL_KEY
import com.tephra.mc.lastfm.ui.artist.ArtistActivity
import com.tephra.mc.lastfm.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    companion object {
        private var SAVED_LAYOUT_MANAGER_KEY = "SAVED_LAYOUT_MANAGER"
    }

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var listView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private var layoutManagerSavedState: Parcelable? = null
    private lateinit var searchListAdapter: SearchListAdapter

    private var onSearchItemClickListener : ISearchItemOnClickListener = object : ISearchItemOnClickListener {
        override fun onClick(v: View, id: String, imageUrl: String) {
            navigateToArtist(v, id, imageUrl)
            layoutManagerSavedState = layoutManager.onSaveInstanceState()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupViewModel()
        initViews()
    }

    private fun setupViewModel() {

        searchViewModel = ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java]

        searchViewModel.initialArtists.observe(this, Observer<Resource<SearchResults>> {
            updateUI(it!!)
        })

        searchViewModel.newArtists.observe(this, Observer<Resource<SearchResults>> {
            appendNextResultsToList(it!!)
        })
    }

    private fun initViews() {

        listView = recycler_list
        layoutManager = LinearLayoutManager(this)
        listView.layoutManager = layoutManager
    }

    fun onSearchBtnClicked(v:View) {
        progress_bar.visibility = View.VISIBLE
        searchViewModel.searchForArtist(et_search.text.toString())
    }

    private fun updateUI(resource: Resource<SearchResults>) {

        progress_bar.visibility = View.GONE
        when (resource.status) {

            Status.SUCCESS -> {

                resource.data?.results?.artistmatches?.let {
                    updateListWithInitialResults(resource.data!!.results!!.artistmatches.artist)
                }?: run {
                    showError()
                }
            }

            Status.ERROR -> showError(resource.message!!)
        }
    }

    private fun updateListWithInitialResults(articles: List<Artist>) {
        btn_next.visibility = View.VISIBLE
        searchListAdapter = SearchListAdapter(articles as MutableList<Artist>, onSearchItemClickListener)
        listView.adapter = searchListAdapter //SearchListAdapter(articles as MutableList<Artist>, onSearchItemClickListener)
        restoreLayoutManagerPosition()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // save the list view layout manager current position state
        outState!!.putParcelable(SAVED_LAYOUT_MANAGER_KEY, layoutManagerSavedState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        layoutManagerSavedState = savedInstanceState!!.getParcelable(SAVED_LAYOUT_MANAGER_KEY)
    }

    private fun restoreLayoutManagerPosition() {
        if (layoutManagerSavedState != null) {
            layoutManager.onRestoreInstanceState(layoutManagerSavedState)
        }
    }

    private fun navigateToArtist(v: View, id: String, imageUrl:String) {
        val intent = Intent(this, ArtistActivity::class.java)
        intent.putExtra(INTENT_ID_KEY, id)
        intent.putExtra(INTENT_IMAGE_URL_KEY, imageUrl)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, v, getString(R.string.view_transition))
        startActivity(intent, options.toBundle())
    }

    fun onNextBtnClicked(v: View) {
        progress_bar.visibility = View.VISIBLE
        searchViewModel.getNextPage()
    }

    private fun appendNextResultsToList(resource: Resource<SearchResults>) {
        progress_bar.visibility = View.GONE
        when (resource.status) {

            Status.SUCCESS -> {
                resource.data?.results?.artistmatches?.let {
                    // get the current list size before its updated
                    val size = searchListAdapter.itemCount
                    searchListAdapter.addItems(resource.data!!.results!!.artistmatches.artist)
                    // only notify changes from the last position
                    searchListAdapter.notifyItemChanged(size)
                }?: run {
                    // null object found
                    showError(getString(R.string.err_text_no_more_results))
                }
            }

            Status.ERROR -> showError(resource.message!!)
        }
    }
}
