fun main() {

    fun List<String>.generateBinaryValue() = first().foldIndexed(Pair("", "")) { index, acc, char ->
        this
            .groupingBy { it[index] }
            .eachCount()
            .let {
                Pair(
                    acc.first + if (it['0'] ?: 0 > it['1'] ?: 0) '0' else '1',
                    acc.second + if (it['0'] ?: 0 > it['1'] ?: 0) '1' else '0'
                )
            }
    }.run {
        first.toInt(2) * second.toInt(2)
    }

    fun part1(input: List<String>) = input.generateBinaryValue()


    fun part2(input: List<String>) = input.size

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println(part1(testInput))
    check(part1(testInput) == 198)
//   check(part2(testInput) == 0)

    val input = readInput("Day03")
    println(part1(input))
    //   println(part2(input))

}
