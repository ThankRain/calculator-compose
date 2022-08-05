package cn.usfl.calculator.utils

import android.content.res.Resources
import androidx.annotation.StringRes
import cn.usfl.calculator.App

fun string(@StringRes id: Int) = res.getString(id)

fun string(@StringRes id: Int, vararg args: Any?) = String.format(res.getString(id), *args)

val res: Resources get() = App.CONTEXT.resources


/**
 * 将Int转换为对应资源字符
 * Created at 2021/12/16 19:28 by @Xeu
 */
val Int.string get() = string(this)
