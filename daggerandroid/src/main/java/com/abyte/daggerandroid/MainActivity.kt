package com.abyte.daggerandroid

import android.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abyte.daggerandroid.model.IUser
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun fragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    @Inject
    lateinit var user: IUser

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logger.info("user = $user")

        val mainFragment = MainFragment()
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, mainFragment)
        transaction.commit()
    }
}
