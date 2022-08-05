package cn.usfl.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import cn.usfl.calculator.ui.screens.CalculatorScreen
import cn.usfl.calculator.ui.theme.CalculatorTheme
import cn.usfl.calculator.ui.vm.CalculateViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: CalculateViewModel by viewModels()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            val systemUiController = rememberSystemUiController()
            CalculatorTheme {
                systemUiController.setStatusBarColor(Color.Transparent, true)
                CalculatorScreen(vm)
            }
        }
    }

}