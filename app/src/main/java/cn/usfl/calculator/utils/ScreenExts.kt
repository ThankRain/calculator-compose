package cn.usfl.calculator.utils

import android.content.res.Configuration

/**
 * 屏幕相关扩展函数
 */

//是否为竖屏状态
val isPortrait get() = res.configuration.orientation == Configuration.ORIENTATION_PORTRAIT; //获取屏幕方向

