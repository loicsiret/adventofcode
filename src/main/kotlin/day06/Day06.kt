fun main() {
    fun part1(input: List<String>) = input.size

    fun part2(input: List<String>) = input.size

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 0)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
