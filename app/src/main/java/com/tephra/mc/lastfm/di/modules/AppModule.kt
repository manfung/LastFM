package com.tephra.mc.lastfm.di.modules

import android.app.Application
import com.tephra.mc.lastfm.data.repository.ILastFmRepo
import com.tephra.mc.latestnews.data.repository.ApiService
import com.tephra.mc.latestnews.data.repository.LastFmRepo
import com.tephra.mc.latestnews.di.modules.RemoteModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class,
        RemoteModule::class,
        ActivityModule::class
    ])

class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    internal fun provideLastFmRepo(apiService: ApiService): ILastFmRepo {
        return LastFmRepo(apiService)
    }
}