package day07

import println
import readInput
import splitBySpace

private val Card = mapOf(
    'A' to 'N',
    'K' to 'M',
    'Q' to 'L',
    'J' to 'K',
    'T' to 'J',
    '9' to 'I',
    '8' to 'H',
    '7' to 'G',
    '6' to 'F',
    '5' to 'E',
    '4' to 'D',
    '3' to 'C',
    '2' to 'B',
).toMutableMap()

private data class Hand(
    val cards: String,
    val bid: Int,
    val joker: Boolean,
): Comparable<Hand> {
    val type by lazy { this.type() }
    val cardValues = Card.also {
        if (joker) it.replace('J', 'A')
    }

    override fun compareTo(other: Hand): Int {
        val co = other.type.compareTo(type)
        if (co != 0) return co
        return other.cards.mapNotNull { cardValues[it] }.joinToString("")
                .compareTo(cards.mapNotNull { cardValues[it] }.joinToString(""))
    }
}

private fun Hand.type(): String {
    return cards.groupingBy { it }.eachCount()
            .map { if (joker && it.key == 'J') 0 else it.value }
            .sortedDescending()
            .let { count ->
                "${count.first() + (cards.count { it == 'J' }.takeIf { joker } ?: 0)}${count.getOrElse(1) { 0 }}"
            }
}

private fun List<String>.readHands(joker: Boolean): List<Hand> {
    return this.map { line ->
        val (cards, bid) = line.splitBySpace()
        Hand(cards, bid.toInt(), joker)
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.readHands(false)
                .sortedDescending()
                .foldIndexed(0) { index, acc, hand ->
                    acc + (hand.bid * (index + 1))
                }
    }

    fun part2(input: List<String>): Int {
        return input.readHands(true)
                .sortedDescending()
                .foldIndexed(0) { index, acc, hand ->
                    acc + (hand.bid * (index + 1))
                }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day07","Day07_test")
    check(part1(testInput1) == 6440)

    val testInput2 = readInput("day07","Day07_2_test")
    check(part2(testInput2) == 5905)

    val input1 = readInput("day07", "Day07")
    part1(input1).println()

    val input2 = readInput("day07", "Day07_2")
    part2(input2).println()
}
