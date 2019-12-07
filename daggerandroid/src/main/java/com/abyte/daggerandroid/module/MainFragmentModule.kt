package com.abyte.daggerandroid.module

import com.abyte.daggerandroid.model.Author
import dagger.Module
import dagger.Provides

@Module
class MainFragmentModule {

    @Provides
    fun provideUser(): Author {
        return Author("哈哈哈", 30)
    }

}