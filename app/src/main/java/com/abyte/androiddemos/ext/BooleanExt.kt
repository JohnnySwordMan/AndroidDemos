package com.abyte.androiddemos.ext

sealed class BooleanExt<out T>

object Otherwise : BooleanExt<Nothing>()
class Data<T>(val data: T) : BooleanExt<T>()

inline fun <T> Boolean.yes(block: () -> T) =
    when {
        this -> {
            Data(block())
        }
        else -> {
            Otherwise
        }
    }

/**
 * 等同于上述写法
 */
//inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> {
//    return if (this) {
//        Data(block())
//    } else {
//        Otherwise
//    }
//}


inline fun <T> Boolean.no(block: () -> T) = when {
    this -> {
        Otherwise
    }
    else -> {
        Data(block())
    }
}

inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
    when (this) {
        is Otherwise -> block()
        is Data -> this.data
    }