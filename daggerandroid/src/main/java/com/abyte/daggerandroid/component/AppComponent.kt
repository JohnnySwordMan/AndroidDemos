package com.abyte.daggerandroid.component

import com.abyte.daggerandroid.DaApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        BindMainActivityModule::class]
)
interface AppComponent {
    fun inject(application: DaApplication)
}