fun MutableList<Int>.popBabies(): MutableList<Int> {
    return this.map {
        if (it == 0) 6 else it - 1
    }.toMutableList()
}

fun computeBabies(initialState: String, max: Int): List<Int> {
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

fun part1Test() {
    val initialState = "3,4,3,1,2"
    check(computeBabies(initialState, 1).toString() == "[2, 3, 2, 0, 1]")
    check(computeBabies(initialState, 2).toString() == "[1, 2, 1, 6, 0, 8]")
    check(computeBabies(initialState, 4).toString() == "[6, 0, 6, 4, 5, 6, 7, 8, 8]")
}

fun main() {
    fun part1(input: String, days: Int) = computeBabies(input, days).size

    fun part2(input: List<String>) = input.size

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    part1Test()
    check(part1(testInput.first(), 80) == 5934)
//    check(part2(testInput) == 0)

    val input = readInput("Day06")
    println(part1(input.first(), 80))
//    println(part2(input))
}
