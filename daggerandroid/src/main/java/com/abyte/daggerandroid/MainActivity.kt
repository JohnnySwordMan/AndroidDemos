package com.abyte.daggerandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abyte.daggerandroid.model.IUser
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var user: IUser

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logger.info("user = $user")
    }
}
