import kotlin.reflect.KFunction1

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

    fun computeValue(input: List<String>, vitals: KFunction1<Map<Char, Int>, Char>) =
        input.first().foldIndexed(input) { index, acc, char ->
            acc
                .groupingBy { it[index] }
                .eachCount()
                .let {
                    vitals(it)
                }
                .let {
                    if (acc.size == 1) acc
                    else acc.filterByCharAt(index, it)
                }
        }.first()

    fun c02(input: List<String>) = computeValue(input, ::lessUsedChar)
    fun oxygen(input: List<String>) = computeValue(input, ::mostUsedChar)

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

fun mostUsedChar(map: Map<Char, Int>): Char {
    val nb0 = map['0'] ?: 0
    val nb1 = map['1'] ?: 0
    return when {
        nb0 == nb1 -> '1'
        nb0 > nb1 -> '0'
        else -> '1'
    }
}

fun lessUsedChar(map: Map<Char, Int>): Char {
    val nb0 = map['0'] ?: 0
    val nb1 = map['1'] ?: 0
    return when {
        nb0 == nb1 -> '0'
        nb0 > nb1 -> '1'
        else -> '0'
    }
}
