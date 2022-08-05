package cn.usfl.calculator.utils

//从Start开始快速正向查找指定的Char的位置，不存在返回null
fun CharSequence.indexOfChars(start: Int = 0, end: Int = length - 1, vararg char: Char): Int? {
    for (i in start..end) {
        if (this.length > i) {
            if (this[i] in char) return i
        } else {
            return null
        }
    }
    return null
}

//从Start开始快速反向查找指定的Char的位置，不存在返回null
fun CharSequence.lastIndexOfChars(
    start: Int = length - 1,
    end: Int = 0,
    chars: Array<Char>
): Int? {
    for (i in start downTo end) {
        if (this[i] in chars) return i
    }
    return null
}

fun CharSequence.countPair(first: Char, second: Char): Pair<Int, Int> {
    var firstCounter = 0
    var secondCounter = 0
    for (i in this) {
        when (i) {
            first -> firstCounter++
            second -> secondCounter++
        }
    }
    return Pair(firstCounter, secondCounter)
}