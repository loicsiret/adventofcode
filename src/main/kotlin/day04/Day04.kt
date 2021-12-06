import day04.BingoGrid

fun main() {

    fun init(input: List<String>): Pair<List<Int>, List<BingoGrid>> =
        Pair(
            input.asSequence().first().split(",").map { it.toInt() },
            input.asSequence()
                .drop(1)
                .chunked(6)
                .map { gridInput ->
                    val grid = BingoGrid()
                    gridInput.drop(1)
                        .forEachIndexed { _, line -> grid.addRow(line) }
                    grid
                }.toList()
        )

    fun part1(input: List<String>): Int {
        val (bingoNumbers, grids) = init(input)

        val (number, winingBoard) = bingoNumbers.firstNotNullOf { number ->
            grids.firstOrNull { grid ->
                grid.find(number)?.let {
                    grid.mark(it.first, it.second)
                    grid.isCompletedGrid(it.first, it.second)
                } ?: false
            }?.let { Pair(number, it) }
        }

        return winingBoard.remainingValues() * number
    }

    fun part2(input: List<String>): Int {
        val (bingoNumbers, grids) = init(input)
        val gridsPool = grids.toMutableList()

        val (number, loosingBoard) = bingoNumbers.firstNotNullOf { number ->
            grids.firstOrNull { grid ->
                grid.find(number)?.let {
                    grid.mark(it.first, it.second)
                    if (grid.isCompletedGrid(it.first, it.second))
                        gridsPool.remove(grid)
                }.let{gridsPool.size == 0 }
            }?.let {
                Pair(number, it)
            }
        }

        return loosingBoard.remainingValues() * number
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}