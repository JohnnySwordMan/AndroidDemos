package com.abyte.daggerandroid.component

import android.app.Activity
import com.abyte.daggerandroid.MainActivity
import com.abyte.daggerandroid.module.MainActivityModule
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Subcomponent(modules = [MainActivityModule::class])
interface MainActivitySubComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>() {}
}

@Module(subcomponents = [MainActivitySubComponent::class])
abstract class BindMainActivityModule {


    // *，不能写成<out Activity>
    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(builder: MainActivitySubComponent.Builder): AndroidInjector.Factory<*>

}