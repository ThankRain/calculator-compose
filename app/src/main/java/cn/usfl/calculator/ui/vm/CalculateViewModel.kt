package cn.usfl.calculator.ui.vm

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculateViewModel : ViewModel() {
    val formula = mutableStateOf("")

    //历史记录
    val history = mutableStateListOf<String>()
}