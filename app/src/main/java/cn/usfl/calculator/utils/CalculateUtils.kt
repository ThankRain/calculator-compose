package cn.usfl.calculator.utils

import android.util.Log
import java.text.DecimalFormat
import java.util.*

val operator = listOf("+", "-", "×", "÷")
fun String.trimSign(next: String): String {
    if (isNotEmpty()) {
        if (contains('∞') || contains("NaN")) return next
        //去除两个重复的运算符
        if (next in operator || next == "") {
            when (this[length - 1]) {
                '+', '-', '×', '÷' -> return (substring(0, length - 1) + next).run {
                    if (next == "") trimSign("") else this
                }
            }
        }
        //百分号 '%' 和反小括号 ')' 必须位于数字末尾
        if (this[length - 1] in arrayOf('%', ')') && next !in operator) return this
        val lastDot = lastIndexOfAny(strings = listOf("+", "-", "×", "÷", "."))
        //避免两个小数点同时出现
        if (lastDot != -1 && this[lastDot] == '.' && next == ".") return this
        return this + next
    }
    return next
}

object CalculateUtils {
    //去除末尾的运算符

    //各个运算符的优先级
    private fun priority(c: Char): Int {
        return when (c) {
            '+', '-' -> 1
            '×', '÷' -> 2
            '(' -> 0
            else -> 0
        }
    }

    // 中缀表达式转逆波兰表达式
    fun calculate(raw: String): String {
        println("RAW:$raw")
        // 111 + 222 - 333 * 444 / 555
        // 采用逆波兰表达式计算
        val formula = "0$raw"
        val numberStack: Stack<String> = Stack() //数字栈
        val operatorStack: Stack<Char> = Stack() //操作符栈
        var value = StringBuilder()
        for (c in formula.toCharArray()) {
            if (c == '+' || c == '-' || c == '×' || c == '÷' || c == '(' || c == ')') {
                //c为运算符
                if (value.isNotEmpty()) numberStack.add(value.toString())
                value = StringBuilder() //重置
                if (operatorStack.isNotEmpty()) {
                    val last: Char = operatorStack[operatorStack.size - 1] //上一个操作符
                    if (c == ')') {
                        var pop: Char = operatorStack.pop()
                        while (pop != '(') {
                            numberStack.push(pop.toString())
                            pop = operatorStack.pop()
                        }
                    } else {
                        while (c != '(' && priority(last) > priority(c) && !operatorStack.empty()) {
                            //上一个操作符优先级高
                            //入栈到num栈中
                            operatorStack.pop()
                            numberStack.push(last.toString())
                        }
                    }
                }
                if (c != ')')
                    operatorStack.push(c)
            } else {
                value.append(c)
            }
        }
        if (value.isNotEmpty()) {
            numberStack.add(value.toString())
        }
        while (!operatorStack.empty()) {
            numberStack.push(java.lang.String.valueOf(operatorStack.pop()))
        }
        //调用逆波兰表达式计算函数，返回计算结果
        return DecimalFormat("#.######").format(calculateRPN(numberStack))
    }

    // 计算逆波兰表达式并返回最终结果
    private fun calculateRPN(stack: Stack<String>): Float {
        val numStack: Stack<Float> = Stack()
        Log.i("Stack", "calculateRPN: $stack") //打印逆波兰表达式
        for (s in stack) {
            try {
                //将百分号转换为科学计数
                val v = s.replace("%", "E-2").toFloat()
                numStack.push(v)
            } catch (e: Exception) {
                //不是数字，为运算符
                val b: Float = numStack.pop()
                val a: Float = numStack.pop()
                when (s) {
                    "+" -> numStack.push(a + b)
                    "-" -> numStack.push(a - b)
                    "×" -> numStack.push(a * b)
                    "÷" -> numStack.push(a / b)
                }
            }
        }
        return numStack.pop()
    }
}