package com.abyte.androiddemos.widgets

import android.text.Editable
import android.text.TextWatcher

/**
 * adapter适配器，可以参考属性动画的一些监听Adapter，
 * 然后子类继承TextWatcherAdapter，这样可以让子类有选择性的实现某些方法，
 * 如果直接实现TextWatcher，则需要实现全部的抽象方法，可能子类只关心afterTextChanged方法
 */
open class TextWatcherAdapter : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}