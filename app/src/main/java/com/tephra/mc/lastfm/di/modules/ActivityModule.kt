package com.tephra.mc.lastfm.di.modules

import com.tephra.mc.lastfm.ui.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun provideSearchActivity(): SearchActivity

}