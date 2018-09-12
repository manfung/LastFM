package com.tephra.mc.lastfm

import android.app.Activity
import android.app.Application
import com.tephra.mc.lastfm.di.modules.AppModule
import com.tephra.mc.latestnews.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class LastFMApp: Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().appModule(AppModule(this)).build().inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}