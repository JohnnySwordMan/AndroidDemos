package com.abyte.daggerandroid

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abyte.daggerandroid.model.Author
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var author: Author

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
    }
}