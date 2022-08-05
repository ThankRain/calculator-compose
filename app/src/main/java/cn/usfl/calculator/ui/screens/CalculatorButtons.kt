package cn.usfl.calculator.ui.screens

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import cn.usfl.calculator.R
import cn.usfl.calculator.utils.*

data class ButtonData(
    @StringRes val text: Int,
    val fontColor: ColorType = ColorType.Normal,
    val backgroundColor: ColorType = ColorType.Normal,
    val onClick: (MutableState<String>, (String?) -> Unit) -> Unit = { it, _ ->
        it.value = it.value.trimSign(string(text))
    }
)

enum class ColorType {
    OnAccent, Accent, Normal,
}

val buttons = listOf(
    ButtonData(
        (R.string.clear), fontColor = ColorType.Accent
    ) { it, commit ->
        //清除
        if (it.value.isNotEmpty()) {
            it.value = ""
        } else {
            commit(null)
        }
    },
    ButtonData(
        (R.string.backspace), fontColor = ColorType.Accent
    ) { it, _ ->
        //回退
        it.value = it.value.run {
            if (isNotEmpty()) substring(0, length - 1)
            else this
        }
    },
    //百分号%
    ButtonData(
        (R.string.percent), fontColor = ColorType.Accent
    ) { it, _ ->
        if ((it.value.isNotEmpty() && it.value[it.value.length - 1] in '0'..'9')) {
            it.value = it.value + '%'
        }
    },
    //除号÷
    ButtonData(
        (R.string.divide), fontColor = ColorType.Accent
    ),
    //7
    ButtonData(
        (R.string.seven)
    ),
    //8
    ButtonData(
        (R.string.eight)
    ),
    //9
    ButtonData(
        (R.string.nine)
    ),
    //乘号×
    ButtonData(
        (R.string.multiplication), fontColor = ColorType.Accent
    ),
    //4
    ButtonData(
        (R.string.four)
    ),
    //5
    ButtonData(
        (R.string.five)
    ),
    //6
    ButtonData(
        (R.string.six)
    ),
    //减号
    ButtonData(
        (R.string.minus), fontColor = ColorType.Accent
    ),
    //1
    ButtonData(
        (R.string.one)
    ),
    //2
    ButtonData(
        (R.string.two)
    ),
    //3
    ButtonData(
        (R.string.three)
    ),
    //加号
    ButtonData(
        (R.string.plus), fontColor = ColorType.Accent
    ),
    //括号
    ButtonData(
        (R.string.brackets), fontColor = ColorType.Accent
    ) { it, _ ->
        if ((it.value.isNotEmpty() && it.value[it.value.length - 1].toString() in operator.plus("(")) || it.value.isEmpty()) {
            it.value = it.value + '('
            return@ButtonData
        }
        val (first, second) = it.value.countPair('(', ')')
        if (first > second && it.value[it.value.length - 1] != '(') {
            it.value = it.value + ")"
        }
    },
//0
    ButtonData(
        (R.string.zero)
    ),
//小数点
    ButtonData(
        (R.string.dot)
    ),
//等于 =
    ButtonData(
        (R.string.equal), backgroundColor = ColorType.Accent, fontColor = ColorType.OnAccent
    ) { it, commit ->
        val raw = it.value.trimSign("")
            .replace("(+", "(0+")
            .replace("(-", "(0+")
            .replace("(×", "(0÷")
            .replace("(÷", "(0×")
            .run {
                val (first, second) = countPair('(', ')')
                if (first > second) {
                    this + ")".repeat(first - second)
                } else this
            }
        if (raw.indexOfChars(char = charArrayOf('+', '-', '×', '÷', '%')) != null) {
            val result = CalculateUtils.calculate(raw).replace(",", "")
            commit("$raw=$result")
            it.value = result
        }
    },
)