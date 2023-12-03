fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day03","Day03_test")
    check(part1(testInput1) == 0)

    val testInput2 = readInput("day03","Day03_2_test")
    check(part2(testInput2) == 0)

    val input1 = readInput("day03", "Day03")
    part1(input1).println()

    val input2 = readInput("day03", "Day03_2")
    part2(input2).println()
}
