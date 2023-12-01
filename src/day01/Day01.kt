package day01

import println
import readInput

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

fun main() {

    fun part1(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            acc + line.extractCalibrationValues()
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01","Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("day01", "Day01")
    part1(input).println()
    part2(input).println()
}
