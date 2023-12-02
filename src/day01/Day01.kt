package day01

import println
import readInput

sealed class NumberState {
    data object Promise : NumberState()
    data class Number(val num: Char) : NumberState()
    data class Discard(val updated: String) : NumberState()
}

private val Numbers = mapOf(
        "one" to '1',
        "two" to '2',
        "three" to '3',
        "four" to '4',
        "five" to '5',
        "six" to '6',
        "seven" to '7',
        "eight" to '8',
        "nine" to '9',
)

private val maxNumberLength: Int
    get() = Numbers.keys.maxOf {
        it.length
    }

private fun String.hasNumber(reversed: Boolean): NumberState {
    val curatedNumber = if (reversed) this.reversed() else this
    if (curatedNumber.length > maxNumberLength) return NumberState.Discard("")

    val num = Numbers[curatedNumber]
    if (num != null) return NumberState.Number(num)

    val isPromiseOfNumber = Numbers.keys.any {
        it.contains(curatedNumber)
    }

    return if (isPromiseOfNumber) NumberState.Promise else NumberState.Discard(this.drop(1))
}

private fun String.extractCalibrationValues(): Int {
    var code: Char
    var remainingCalibrationLine = this
    var twoDigitNumber = ""

    while (remainingCalibrationLine.isNotBlank() && twoDigitNumber.length < 2) {
        code = remainingCalibrationLine.first()
        remainingCalibrationLine = remainingCalibrationLine.drop(1)

        if (code.isDigit()) {
            twoDigitNumber += code
            remainingCalibrationLine = remainingCalibrationLine.reversed()
        }
    }

    if (twoDigitNumber.length == 1) {
        twoDigitNumber += twoDigitNumber
    }

    return twoDigitNumber.toInt()
}

private fun String.extractCalibrationValuesV2(): Int {
    var code: Char
    var letterNum = ""
    var remainingCalibrationLine = this
    var twoDigitNumber = ""
    var reversedLine = false

    while (remainingCalibrationLine.isNotBlank() && twoDigitNumber.length < 2) {
        code = remainingCalibrationLine.first()
        letterNum += code
        remainingCalibrationLine = remainingCalibrationLine.drop(1)

        if (code.isDigit()) {
            twoDigitNumber += code
            letterNum = ""
            remainingCalibrationLine = remainingCalibrationLine.reversed()
            reversedLine = true
        }

        when (val number = letterNum.hasNumber(reversedLine)) {
            NumberState.Promise -> Unit
            is NumberState.Discard -> {
                letterNum = number.updated
            }
            is NumberState.Number -> {
                twoDigitNumber += number.num
                letterNum = ""
                remainingCalibrationLine = remainingCalibrationLine.reversed()
                reversedLine = true
            }
        }
    }

    if (twoDigitNumber.length == 1) {
        twoDigitNumber += twoDigitNumber
    }

    return twoDigitNumber.toInt()
}

fun main() {

    fun part1(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            acc + line.extractCalibrationValues()
        }
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            acc + line.extractCalibrationValuesV2()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day01","Day01_test")
    check(part1(testInput1) == 142)

    val testInput2 = readInput("day01","Day01_2_test")
    check(part2(testInput2) == 281)

    val input1 = readInput("day01", "Day01")
    part1(input1).println()

    val input2 = readInput("day01", "Day01_2")
    part2(input2).println()
}