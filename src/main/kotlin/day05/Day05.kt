typealias Coordinate = Pair<Int, Int>

fun main() {
    fun computeLines(
        input: List<String>,
        resultingMap: MutableMap<Coordinate, Int>,
        includeDiag: Boolean
    ): Int {
        input
            .map { line ->
                line.split(" -> ")
                    .flatMap { coordinates ->
                        coordinates.split(",")
                            .zipWithNext { a, b -> Coordinate(a.toInt(), b.toInt()) }
                    }.chunked(2)
                    .fold(resultingMap) { acc, list ->
                        val newCoordinates = mutableSetOf<Coordinate>()
                        val (x1, y1) = list[0]
                        val (x2, y2) = list[1]
                        if (includeDiag || (x1 != x2).xor(y1 != y2)) {
                            when {
                                y1 == y2 -> {
                                    for (i in x1 toward  x2) {
                                        newCoordinates.add(Coordinate(i, y1))
                                    }
                                }
                                x1 == x2 -> {
                                    for (i in y1 toward y2 ) {
                                        newCoordinates.add(Coordinate(x1, i))
                                    }
                                }
                                else -> {
                                    (x1 toward x2)
                                        .zip(y1 toward y2)
                                        .forEach {
                                            newCoordinates.add(Coordinate(it.first, it.second))
                                        }
                                }
                            }
                        }
                        newCoordinates.map {
                            acc.merge(it, 1, Int::plus)
                        }
                        acc
                    }
                resultingMap
            }
        return resultingMap.count { it.value > 1 }
    }

    fun part1(input: List<String>): Int {
        val resultingMap = mutableMapOf<Coordinate, Int>()
        return computeLines(input, resultingMap, false)
    }

    fun part2(input: List<String>): Int {
        val resultingMap = mutableMapOf<Coordinate, Int>()
        return computeLines(input, resultingMap, true)
    }
// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
private infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}
