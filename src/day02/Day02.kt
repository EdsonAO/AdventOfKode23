import kotlin.math.max

private val MaxValues = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14,
)

private fun String.id(): Int {
    return this.split(' ').last().toInt()
}

private fun String.setExceedQuantity(): Boolean {
    this.split(',').forEach {
        val (quantity, color) = it.trim().split(' ')
        val maxValue = MaxValues[color] ?: return true
        if (quantity.toInt() > maxValue) {
            return true
        }
    }

    return false
}

private fun String.extractQuantities(): Map<String, Int> {
    val map = mutableMapOf<String, Int>()
    this.split(',').forEach {
        val (quantity, color) = it.trim().split(' ')
        map[color] = quantity.toInt()
    }

    return map.toMap()
}

private fun String.setOfCubes(): List<String> {
    return this.split(';')
}

private fun MutableCollection<Int>.times(): Int {
    return this.fold(1) { acc, i -> acc * i }
}

fun main() {
    //Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    fun part1(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            var possible = true
            val (pre, post) = line.split(':')
            val id = pre.id()
            post.setOfCubes().forEach {
                if (it.setExceedQuantity()) {
                    possible = false
                }
            }

            acc + if (possible) id else 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            val temp = mutableMapOf(
                    "red" to 0,
                    "green" to 0,
                    "blue" to 0,
            )
            val (_, post) = line.split(':')
            post.setOfCubes().forEach {
                it.extractQuantities().forEach { map ->
                    val innerValue = temp[map.key] ?: 0
                    temp[map.key] = max(innerValue, map.value)
                }

            }

            acc + temp.values.times()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day02","Day02_test")
    check(part1(testInput1) == 8)

    val testInput2 = readInput("day02","Day02_2_test")
    check(part2(testInput2) == 2286)

    val input1 = readInput("day02", "Day02")
    part1(input1).println()

    val input2 = readInput("day02", "Day02_2")
    part2(input2).println()
}
