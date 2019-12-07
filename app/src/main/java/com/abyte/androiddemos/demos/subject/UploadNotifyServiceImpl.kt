package com.abyte.code.demos.subject

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * 使用rx替代回调，在一些复杂场景，不太合适使用回调的，可以使用rx。
 * 其实rx类似于EventBus，在使用的过程中，需要控制好，不要随意的发送数据
 */
class UploadNotifyServiceImpl : IUploadNotifyService {


    private val uploadVideo = PublishSubject.create<Int>()

    override fun notifyUpload(id: Int) {
        uploadVideo.onNext(id)
    }

    override fun videoUpload(): Observable<Int> = uploadVideo

}