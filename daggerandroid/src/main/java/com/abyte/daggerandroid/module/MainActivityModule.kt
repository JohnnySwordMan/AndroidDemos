package com.abyte.daggerandroid.module

import com.abyte.daggerandroid.model.IUser
import com.abyte.daggerandroid.model.User
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideIUser(): IUser {
        return User("geyan", 20)
    }

}