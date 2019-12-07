package com.abyte.daggerandroid.component

import com.abyte.daggerandroid.MainFragment
import com.abyte.daggerandroid.module.MainFragmentModule
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap


//@Subcomponent(modules = [MainFragmentModule::class])
//interface MainFragmentSubComponent : AndroidInjector<MainFragment> {
//
//    @Subcomponent.Builder
//    abstract class Builder : AndroidInjector.Builder<MainFragment>() {}
//}


//@Module(subcomponents = [MainFragmentSubComponent::class])
@Module
abstract class BindMainFragmentModule {


    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun contributeMainFragment(): MainFragment


    // *，不能写成<out Activity>
//    @Binds
//    @IntoMap
//    @ClassKey(MainFragment::class)
//    abstract fun bindMainFragmentInjector(builder: MainFragmentSubComponent.Builder): AndroidInjector.Factory<*>

}