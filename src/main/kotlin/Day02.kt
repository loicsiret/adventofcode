fun main() {
    fun part1(input: List<String>) = input.size

    fun part2(input: List<String>) = input.size

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 0)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
