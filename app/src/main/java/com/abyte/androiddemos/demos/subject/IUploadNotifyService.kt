package com.abyte.code.demos.subject

import io.reactivex.Observable

interface IUploadNotifyService {

    fun notifyUpload(id: Int)

    fun videoUpload(): Observable<Int>
}