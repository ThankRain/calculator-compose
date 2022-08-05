package cn.usfl.calculator.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cn.usfl.calculator.App
import com.google.accompanist.insets.LocalWindowInsets

/**
 * 屏幕相关扩展函数
 */

//获取屏幕宽度 px
fun width(fraction: Float = 1.0f) =
    (App.CONTEXT.getSystemService(Context.WINDOW_SERVICE) as WindowManager).run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            maximumWindowMetrics.bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            defaultDisplay.getRealMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    } * fraction

//获取屏幕高度 px
fun height(fraction: Float = 1.0f) =
    (App.CONTEXT.getSystemService(Context.WINDOW_SERVICE) as WindowManager).run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            maximumWindowMetrics.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            defaultDisplay.getRealMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    } * fraction

@Composable
fun statusHeight() = LocalWindowInsets.current
    .statusBars.run { bottom - top }

@Composable
fun statusHeightDp() = statusHeight().px2dp

//是否为竖屏状态
val isPortrait get() = res.configuration.orientation == Configuration.ORIENTATION_PORTRAIT; //获取屏幕方向

//获取屏幕宽度 dp
fun widthDp(fraction: Float = 1.0f) = width(fraction).px2dp

//获取屏幕宽度 dp
fun minDp(fraction: Float = 1.0f) = if (isPortrait) widthDp(fraction) else heightDp(fraction)

//获取屏幕高度 dp
fun heightDp(fraction: Float = 1.0f) = height(fraction).px2dp

//dp 转 px(float)
val Dp.dp2px get() = this.value * Resources.getSystem().displayMetrics.density
val Int.dp2px get() = this * Resources.getSystem().displayMetrics.density

//px 转 Dp
val Float.px2dp get() = (this / Resources.getSystem().displayMetrics.density).dp

val Int.px2dp get() = (this * 1f / Resources.getSystem().displayMetrics.density).dp

val density get() = Resources.getSystem().displayMetrics.density