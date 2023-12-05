package day04

import println
import readInput
import kotlin.math.pow

data class Card(
    val number: Int,
    val winner: List<Int>,
    val obtained: List<Int>,
    val winNumbers: List<Int>,
)

private fun List<String>.mapToCards(): List<Card> {
    return this.map { line ->
        val card = line.split(':')
        val cardNumber = card.first().trim().split("\\s+".toRegex()).last().toInt()
        val numbers = card.last().split('|')
        val winning = numbers.first().trim().split("\\s+".toRegex()).map { it.toInt() }
        val obtained = numbers.last().trim().split("\\s+".toRegex()).map { it.toInt() }
        Card(
            number = cardNumber,
            winner = winning,
            obtained = obtained,
            winNumbers = obtained.filter { it in winning },
        )
    }
}

private fun List<Int>.calculateWorth(): Int {
    if (isEmpty()) return 0
    if (size == 1) return 1
    return this.toMutableList().drop(1).foldIndexed(1) { index, acc, _ ->
        acc + 2.0.pow(index).toInt()
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.mapToCards().sumOf { card ->
            card.winNumbers.calculateWorth()
        }
    }

    fun part2(input: List<String>): Int {
        val result = MutableList(input.size) { 1 }
        input.mapToCards().mapIndexed { index, card ->
            (1..card.winNumbers.count()).forEach { instance ->
                result[index + instance] += result[index]
            }
        }
        return result.sum()
    }


    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day04","Day04_test")
    check(part1(testInput1) == 13)

    val testInput2 = readInput("day04","Day04_2_test")
    check(part2(testInput2) == 30)

    val input1 = readInput("day04", "Day04")
    part1(input1).println()

    val input2 = readInput("day04", "Day04_2")
    part2(input2).println()
}
