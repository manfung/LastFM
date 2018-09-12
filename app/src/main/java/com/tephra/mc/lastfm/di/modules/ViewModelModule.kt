package com.tephra.mc.lastfm.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tephra.mc.lastfm.di.ViewModelFactory
import com.tephra.mc.lastfm.di.ViewModelKey
import com.tephra.mc.lastfm.ui.artist.ArtistViewModel
import com.tephra.mc.lastfm.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ArtistViewModel::class)
    internal abstract fun bindArtistViewModel(newsListViewModel: ArtistViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindArticleViewModel(articleViewModel: SearchViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
