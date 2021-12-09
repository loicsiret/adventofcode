fun main() {
    fun part1(input: String, days: Long) = computeBabies(input, days).size

    fun part2(input: String, days: Long) = computeBabiesOptimize(input, days)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    part1Test()
    part2Test()
    check(part1(testInput.first(), 80) == 5934)
    check(part2(testInput.first(), 256) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input.first(), 80))
    println(part2(input.first(), 256))
}

fun MutableList<Int>.popBabies(): MutableList<Int> {
    return this.map {
        if (it == 0) 6 else it - 1
    }.toMutableList()
}

fun computeBabies(initialState: String, max: Long): List<Int> {
    var list = initialState.split(",")
        .map { it.toInt() }
        .toMutableList()

    var paddWithFiller = 0
    for (i in 1..max) {
        list = list.popBabies()
        for (j in 1..paddWithFiller) list.add(8)
        paddWithFiller = list.filter { it == 0 }.count()
    }
    return list
}

fun computeBabiesOptimize(initialState: String, max: Long): Long {
    var list: MutableMap<Int, Long> = initialState.split(",")
        .map { it.toInt() }
        .groupingBy { it }
        .eachCountLong()
        .toMutableMap()

    var paddWithFiller = 0L

    for (i in 1..max) {
        println("day $i : $list")
        list = list.mapKeys { it.key - 1 }.toMutableMap()
        list.merge(8, paddWithFiller, Long::plus)
        list.merge(6, list.getOrDefault(-1, 0), Long::plus)
        list.remove(-1)
        paddWithFiller = list.getOrDefault(0, 0)
    }
    return list.values.sum().also { println(it) }
}

fun part1Test() {
    val initialState = "3,4,3,1,2"
    check(computeBabies(initialState, 1).toString() == "[2, 3, 2, 0, 1]")
    check(computeBabies(initialState, 2).toString() == "[1, 2, 1, 6, 0, 8]")
    check(computeBabies(initialState, 4).toString() == "[6, 0, 6, 4, 5, 6, 7, 8, 8]")
}

fun part2Test() {
    val initialState = "3,4,3,1,2"
    check(computeBabiesOptimize(initialState, 1) == 5L)
    check(computeBabiesOptimize(initialState, 2) == 6L)
    check(computeBabiesOptimize(initialState, 4) == 9L)
    check(computeBabiesOptimize(initialState, 5) == 10L)
    check(computeBabiesOptimize(initialState, 9) == 11L)
    check(computeBabiesOptimize(initialState, 12) == 17L)
    check(computeBabiesOptimize(initialState, 18) == 26L)
}

fun <T, K> Grouping<T, K>.eachCountLong(): Map<K, Long> =
// fold(0) { acc, e -> acc + 1 } optimized for boxing
    foldTo(destination = mutableMapOf(),
        initialValueSelector = { _, _ -> kotlin.jvm.internal.Ref.LongRef() },
        operation = { _, acc, _ -> acc.apply { element += 1L } })
        .mapValuesInPlace { it.value.element }

inline fun <K, V, R> MutableMap<K, V>.mapValuesInPlace(f: (Map.Entry<K, V>) -> R): MutableMap<K, R> {
    entries.forEach {
        (it as MutableMap.MutableEntry<K, R>).setValue(f(it))
    }
    return (this as MutableMap<K, R>)
}