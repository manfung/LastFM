package com.tephra.mc.lastfm.ui.search

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.news.mc.com.newsapp.ui.adapter.SearchListAdapter
import com.tephra.mc.lastfm.R
import com.tephra.mc.lastfm.data.model.Artist
import com.tephra.mc.lastfm.data.model.ArtistSearchResults
import com.tephra.mc.lastfm.data.model.SearchResults
import com.tephra.mc.lastfm.data.repository.Resource
import com.tephra.mc.lastfm.data.repository.Status
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

    private var onSearchItemClickListener : ISearchItemOnClickListener = object : ISearchItemOnClickListener {
        override fun onClick(v: View, id: Int) {
            gotoArtist(v, id)
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

        searchViewModel.artists.observe(this, Observer<Resource<SearchResults>> {
            updateUI(it!!)

        })
    }

    private fun initViews() {

        listView = recycler_list
        layoutManager = LinearLayoutManager(this)
        listView.layoutManager = layoutManager
    }

    fun onSearchBtnClicked(v:View) {

        progress_bar.visibility = View.VISIBLE
        // TODO take from edittext and check "" in vm
        searchViewModel.searchByArtist("test")
    }

    private fun updateUI(resource: Resource<SearchResults>) {

        progress_bar.visibility = View.GONE
        when (resource.status) {

            Status.SUCCESS -> {

                resource.data?.results?.artistmatches?.let {
                    updateList(resource.data!!.results!!.artistmatches.artist)
                }?: run {
                    showError()
                }
            }

            Status.ERROR -> showError()
        }
    }

    private fun showError() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    private fun updateList(articles: List<Artist>) {
        listView.adapter = SearchListAdapter(articles, onSearchItemClickListener)
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

}
