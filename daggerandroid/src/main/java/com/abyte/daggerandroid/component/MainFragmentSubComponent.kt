package com.abyte.daggerandroid.component

import com.abyte.daggerandroid.MainFragment
import com.abyte.daggerandroid.module.MainFragmentModule
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap


@Subcomponent(modules = [MainFragmentModule::class])
interface MainFragmentSubComponent : AndroidInjector<MainFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainFragment>() {}
}


@Module(subcomponents = [MainFragmentSubComponent::class])
abstract class BindMainFragmentModule {


    // *，不能写成<out Activity>
    @Binds
    @IntoMap
    @ClassKey(MainFragment::class)
    abstract fun bindMainFragmentInjectorFactory(builder: MainFragmentSubComponent.Builder): AndroidInjector.Factory<*>

}