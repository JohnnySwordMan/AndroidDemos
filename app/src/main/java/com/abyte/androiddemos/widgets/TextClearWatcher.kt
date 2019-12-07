package com.abyte.androiddemos.widgets

import android.text.Editable
import android.view.View
import android.widget.EditText
import org.jetbrains.anko.sdk15.listeners.onClick
import java.lang.ref.WeakReference

/**
 * 这个主要是点击清除按钮，删除所有输入文案,
 * 这里需要使用弱引用，因为edit可能是由Activity或Fragment页面传递过来的
 */
class TextClearWatcher(edit: EditText, clearView: View) : TextWatcherAdapter() {

    private var editRef: WeakReference<EditText>? = null
    private var clearViewRef: WeakReference<View>? = null

    init {
        editRef = WeakReference(edit)
        clearViewRef = WeakReference(clearView)

        clearView.onClick {
            // 点击清除按钮，清空文本
            edit.setText("")
        }
    }

    override fun afterTextChanged(s: Editable?) {
        super.afterTextChanged(s)
        if (s != null && clearViewRef != null && clearViewRef!!.get() != null) {
            if (s.isEmpty()) {
                clearViewRef!!.get()!!.visibility = View.GONE
            } else {
                clearViewRef!!.get()!!.visibility = View.VISIBLE
            }
        }
    }

}