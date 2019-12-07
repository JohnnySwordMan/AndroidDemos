package com.abyte.daggerandroid

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abyte.daggerandroid.model.Author
import com.abyte.daggerandroid.model.IUser
import com.abyte.daggerandroid.model.Login
import dagger.android.AndroidInjection
import dagger.android.DaggerFragment
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    @Inject
    lateinit var author: Author

    @Inject
    lateinit var user: IUser

    @Inject
    lateinit var login: Login

    override fun onAttach(activity: Activity?) {
        AndroidInjection.inject(this)
        super.onAttach(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.error("author = $author")
        Log.e("gy", "author = $author")
        Log.e("gy", "MainFragment-user = $user")
        Log.e("gy", "MainFragment-login = $login")
    }
}