package cn.usfl.calculator.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    surface = Color(0xFF333333),
    onSecondary = Color(0xFFCCCCCC)
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    surface = Color(0xFFF1F1F1),
    onSecondary = Color(0xFF999999)
)

var LocalAppColors = compositionLocalOf {
    LightColorPalette
}

@Stable
val colors: Colors
    @Composable
    get() = LocalAppColors.current

@Composable
fun CalculatorTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val appColors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiCtrl = rememberSystemUiController()
    systemUiCtrl.setStatusBarColor(appColors.background)
    systemUiCtrl.setNavigationBarColor(appColors.background)
    systemUiCtrl.setSystemBarsColor(appColors.background)

    ProvideWindowInsets {
        CompositionLocalProvider(
            LocalAppColors provides appColors,
            LocalIndication provides rememberRipple(),
            content = content
        )
    }
}