package com.abyte.androiddemos.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.abyte.androiddemos.AppContext

/**
 * 剪切板工具类
 */
object ClipPrimaryUtil {

    private val clipManager = AppContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    fun getPrimaryText(): CharSequence? {
        if (clipManager.hasPrimaryClip() && clipManager.primaryClip!!.itemCount > 0) {
            return clipManager.primaryClip!!.getItemAt(0).text
        }
        return null
    }

    fun setPrimaryText(addText: CharSequence) {
        clipManager.setPrimaryClip(ClipData.newPlainText(addText, addText))
    }
}