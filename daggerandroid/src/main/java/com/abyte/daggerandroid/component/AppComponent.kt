package com.abyte.daggerandroid.component

import android.app.Application
import com.abyte.daggerandroid.DaApplication
import com.abyte.daggerandroid.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        AppModule::class,
        BindMainActivityModule::class]
)
interface AppComponent {
    fun inject(application: DaApplication)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}