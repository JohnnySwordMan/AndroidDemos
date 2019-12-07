package com.abyte.androiddemos.demos.subject

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.abyte.androiddemos.R
import com.abyte.code.demos.subject.UploadNotifyServiceImpl
import kotlinx.android.synthetic.main.activity_subject.*
import org.jetbrains.anko.sdk15.listeners.onClick

class SubjectActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)

        val uploadNotifyService = UploadNotifyServiceImpl()

        startUpload.onClick {
            uploadNotifyService.notifyUpload(1)
        }

        uploadNotifyService.videoUpload().subscribe {
            Log.e("test", "value = $it")
        }
    }
}