package com.abyte.daggerandroid

import android.app.Activity
import android.app.Application
import com.abyte.daggerandroid.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class DaApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       return  DaggerAppComponent.builder().application(this).build()
    }


//    @Inject
//    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
//
//    override fun activityInjector(): AndroidInjector<Activity> {
//        return dispatchingActivityInjector


    override fun onCreate() {
        super.onCreate()
//        DaggerAppComponent.create().inject(this)

    }

}