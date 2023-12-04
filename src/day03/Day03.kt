package day03

import println
import readInput

data class Position(
    val row: Int,
    val col: Int,
) {
    val id: String
        get() = "$row:$col"
}

private fun List<String>.symbolLocations(): List<Pair<Char, Position>> = flatMapIndexed { row, line ->
    line.mapIndexedNotNull { index, elem ->
        if (!elem.isDigit() && elem != '.') elem to Position(row, index) else null
    }
}

private fun List<String>.numbers(): List<Pair<Int, List<Position>>> {
    val numberList = emptyList<Pair<Int, List<Position>>>().toMutableList()
    this.forEachIndexed { row, line ->
        "(\\d+)".toRegex().findAll(line).forEach { match ->
            val positions = emptyList<Position>().toMutableList()
            match.range.map { col ->
                positions.add(Position(row, col))
            }
            numberList.add(match.value.toInt() to positions)
        }
    }

    return numberList
}

private fun Position.neighbours(): List<Position> =
        (-1..1).flatMap { x ->
            (-1..1).map { y ->
                Position(row + x, col + y)
            }
        }

fun main() {
    fun part1(input: List<String>): Int {
        val symbolLocation = input.symbolLocations()
        return input.numbers()
                .filter { num ->
                    num.second.any { numPosition ->
                        numPosition.neighbours().any { position ->
                            position.id in symbolLocation.map { it.second.id }
                        }
                    }
                }
                .sumOf { it.first }
    }

    fun part2(input: List<String>): Int {
        val symbolLocation = input.symbolLocations()
        return symbolLocation.filter { (symbol, _) -> symbol == '*' }
                .map { (_, symbolPosition) ->
                    input.numbers().filter { (_, locations) ->
                         symbolPosition.neighbours().any { position ->
                             position.id in locations.map { it.id }
                         }
                    }
                }.filter { it.count() == 2 }
                .sumOf { (a, b) -> a.first * b.first }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day03","Day03_test")
    check(part1(testInput1) == 4361)

    val testInput2 = readInput("day03","Day03_2_test")
    check(part2(testInput2) == 467835)

    val input1 = readInput("day03", "Day03")
    part1(input1).println()

    val input2 = readInput("day03", "Day03_2")
    part2(input2).println()
}
