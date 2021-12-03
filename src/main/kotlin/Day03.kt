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

    fun c02(input: List<String>) = input.first().foldIndexed(input) { index, acc, char ->
        acc
            .groupingBy { it[index] }
            .eachCount()
            .lessUsedChar('0')
            .let {
                if (acc.size == 1) acc
                else acc.filterByCharAt(index, it)
            }
    }.first()

    fun oxygen(input: List<String>) = input.first().foldIndexed(input) { index, acc, char ->
        acc
            .groupingBy { it[index] }
            .eachCount()
            .mostUsedChar('1')
            .let {
                if (acc.size == 1) acc
                else acc.filterByCharAt(index, it)
            }
    }.first()

    fun part2(input: List<String>) = oxygen(input).toInt(2) * c02(input).toInt(2)

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println(part1(testInput))
    check(part1(testInput) == 198)
    println(oxygen(testInput))
    check(oxygen(testInput) == "10111")
    println(c02(testInput))
    check(c02(testInput) == "01010")
    println(part2(testInput))

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

inline fun List<String>.filterByCharAt(index: Int, char: Char) =
    filter {
        it[index] == char
    }

fun Map<Char, Int>.mostUsedChar(tieBreaker: Char = '0'): Char {
    val nb0 = this['0'] ?: 0
    val nb1 = this['1'] ?: 0
    return when {
        nb0 == nb1 -> tieBreaker
        nb0 > nb1 -> '0'
        else -> '1'
    }
}

fun Map<Char, Int>.lessUsedChar(tieBreaker: Char = '0'): Char {
    val nb0 = this['0'] ?: 0
    val nb1 = this['1'] ?: 0
    return when {
        nb0 == nb1 -> tieBreaker
        nb0 > nb1 -> '1'
        else -> '0'
    }
}
