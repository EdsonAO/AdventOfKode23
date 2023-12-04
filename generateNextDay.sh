#!/bin/bash

prevDayNum=$(find "$PWD/src" -name "Day*.kt" | grep -o "Day[0-9][0-9]" | cut -c 4- | sort -n | tail -n 1)
newDayNum=$(printf "%02d" $((10#$prevDayNum + 1)))

mkdir "src/day$newDayNum"
touch "src/day$newDayNum/Day${newDayNum}.txt"
touch "src/day$newDayNum/Day${newDayNum}_2.txt"
touch "src/day$newDayNum/Day${newDayNum}_test.txt"
touch "src/day$newDayNum/Day${newDayNum}_2_test.txt"

cat <<EOL > "$PWD/src/day$newDayNum/Day$newDayNum.kt"
package day${newDayNum}

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day${newDayNum}","Day${newDayNum}_test")
    check(part1(testInput1) == 0)

    val testInput2 = readInput("day${newDayNum}","Day${newDayNum}_2_test")
    check(part2(testInput2) == 0)

    val input1 = readInput("day${newDayNum}", "Day${newDayNum}")
    part1(input1).println()

    val input2 = readInput("day${newDayNum}", "Day${newDayNum}_2")
    part2(input2).println()
}
EOL
