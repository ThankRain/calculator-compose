package cn.usfl.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cn.usfl.calculator.ui.screens.CalculatorScreen
import cn.usfl.calculator.ui.theme.CalculatorTheme
import cn.usfl.calculator.ui.theme.colors
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            CalculatorTheme {
                systemUiController.setStatusBarColor(colors.background)
                CalculatorScreen()
            }
        }
    }
}