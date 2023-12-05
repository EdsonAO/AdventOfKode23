package day05

import day05.Mapper.Companion.extractNextMap
import println
import readInput

data class Mapper(
    val source: UInt,
    val destination: UInt,
    val rangeLength: UInt,
) {
    val range: UIntRange = source ..< source + rangeLength

    companion object {
        fun List<Mapper>.extractNextMap(start: UInt, size: UInt): List<Pair<UInt, UInt>> {
            val listOfBounds = this.flatMap {
                listOf(it.range.first, it.range.last)
            } + listOf(start, start + size)

            return listOfBounds.asSequence().sorted()
                    .dropWhile { it < start }
                    .takeWhile { it <= start + size }
                    .windowed(2)
                    .map { (first, last) ->
                        this.find {
                            first in it.range
                        }?.let {
                            first - it.source + it.destination to (last - first)
                        } ?: (first to (last - first))
                    }.toList()
        }
    }
}

private fun List<String>.fillData(): Pair<List<UInt>, Map<String, List<Mapper>>> {
    val seeds = emptyList<UInt>().toMutableList()
    val mappers = mapOf<String, MutableList<Mapper>>().toMutableMap()
    var currentMapper = ""

    this.forEachIndexed { index, line ->
        if (line.isBlank()) return@forEachIndexed

        if (index == 0) {
            seeds.addAll(line.split(":").last().trim().split(" ").map { it.toUInt() })
            return@forEachIndexed
        }

        if (line.contains("map")) {
            currentMapper = line.trim().split(" ").first()
            mappers[currentMapper] = emptyList<Mapper>().toMutableList()
            return@forEachIndexed
        }

        val (dst, src, rangeLength) = line.trim().split(" ").map { it.toUInt() }
        mappers[currentMapper]?.add(Mapper(src, dst, rangeLength))
    }

    return seeds to mappers
}

fun main() {
    fun part1(input: List<String>): UInt {
        val (seeds, mappers) = input.fillData()
        return seeds.minOf { num ->
            mappers.values.fold(num) { acc, innerMappers ->
                innerMappers.find { acc in it.range }?.let { acc - it.source + it.destination } ?: acc
            }
        }
    }

    fun part2(input: List<String>): UInt {
        val (_, mappers) = input.fillData()
        val seedRanges = input.first().split(":").last().trim().split(" ").chunked(2)
                .map { (seed, range) ->
                    seed.toUInt() to range.toUInt()
                }
        return seedRanges.minOf { seedInput ->
            mappers.values.fold(listOf(seedInput)) { acc, mapper ->
                acc.flatMap { (start, size) ->
                    mapper.extractNextMap(start, size)
                }
            }.minOf { locationInput ->
                locationInput.first
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day05","Day05_test")
    check(part1(testInput1) == 35u)

    val testInput2 = readInput("day05","Day05_2_test")
    check(part2(testInput2) == 46u)

    val input1 = readInput("day05", "Day05")
    part1(input1).println()

    val input2 = readInput("day05", "Day05_2")
    part2(input2).println()
}
