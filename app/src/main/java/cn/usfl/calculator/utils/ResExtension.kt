package cn.usfl.calculator.utils

import android.content.res.Resources
import androidx.annotation.StringRes
import cn.usfl.calculator.App

fun string(@StringRes id: Int) = res.getString(id)

val res: Resources get() = App.CONTEXT.resources