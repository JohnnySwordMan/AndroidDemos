package com.abyte.daggerandroid.component

import com.abyte.daggerandroid.MainActivity
import com.abyte.daggerandroid.module.MainActivityModule
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

// 这样的话，在MainFragment中是可以拿到MainActivityModule中对外提供的对象的
//@Subcomponent(
//    modules = [MainActivityModule::class,
//        BindMainFragmentModule::class]
//)
//interface MainActivitySubComponent : AndroidInjector<MainActivity> {
//
//    @Subcomponent.Builder
//    abstract class Builder : AndroidInjector.Builder<MainActivity>() {}
//}

//@Module(subcomponents = [MainActivitySubComponent::class])
@Module
abstract class BindMainActivityModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class, BindMainFragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity


    // *，不能写成<out Activity>
//    @Binds
//    @IntoMap
//    @ClassKey(MainActivity::class)
//    abstract fun bindMainActivityInjector(builder: MainActivitySubComponent.Builder): AndroidInjector.Factory<*>

}