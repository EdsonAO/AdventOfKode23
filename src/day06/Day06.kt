package day06

import println
import readInput
import splitBySpace

data class Race(
    val time: Long,
    val distance: Long,
)

private fun List<String>.mapToRaces(): List<Race> {
    val times = this.first().split(':').last().splitBySpace().map { it.toLong() }
    val distances = this.last().split(':').last().splitBySpace().map { it.toLong() }
    return times.zip(distances).map { Race(it.first, it.second) }
}

private fun List<String>.mapToSingleRace(): Race {
    val time = this.first().split(':').last().splitBySpace().joinToString("").toLong()
    val distance = this.last().split(':').last().splitBySpace().joinToString("").toLong()
    return Race(time, distance)
}

fun Long.isValid(race: Race): Boolean {
    return (race.time - this) * this > race.distance
}

fun Race.countWays(): Int {
    return (1..<time).fold(0) { acc, speed ->
        acc + if (speed.isValid(this)) 1 else 0
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val races = input.mapToRaces()
        return races
                .map { it.countWays() }
                .fold(1) { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        val race = input.mapToSingleRace()
        return race.countWays()
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day06","Day06_test")
    check(part1(testInput1) == 288)

    val testInput2 = readInput("day06","Day06_2_test")
    check(part2(testInput2) == 71503)

    val input1 = readInput("day06", "Day06")
    part1(input1).println()


    val input2 = readInput("day06", "Day06_2")
    part2(input2).println()
}
