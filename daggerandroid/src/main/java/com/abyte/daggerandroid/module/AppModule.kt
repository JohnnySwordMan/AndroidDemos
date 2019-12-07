package com.abyte.daggerandroid.module

import com.abyte.daggerandroid.model.Login
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideLogin(): Login {
        return Login("呵呵呵呵", "123456")
    }
}