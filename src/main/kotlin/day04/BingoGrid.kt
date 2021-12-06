package day04

internal data class BingoGrid(val matrix: MutableList<MutableList<BingoElement>> = mutableListOf()) {
    private val WHITESPACE = "\\s+".toRegex()

    data class BingoElement(val number: Int, var marked: Boolean = false) {
        fun mark() {
            marked = true
        }
    }

    fun addRow(input: String) {
        matrix.add(
            input.trim()
                .split(WHITESPACE)
                .map {
                    BingoElement(it.toInt())
                }.toMutableList()
        )
    }

    fun find(input: Int): Pair<Int, Int>? {
        matrix.forEachIndexed { x, list ->
            list.forEachIndexed { y, value ->
                if (value.number == input) return Pair(x, y)
            }
        }
        return null
    }

    fun mark(x: Int, y: Int) {
        apply {
            matrix[x][y].mark()
        }
    }

    fun isCompletedGrid(x: Int, y: Int): Boolean {
        val rowCompleted = matrix[x].all { it.marked }
        val columnCompleted = matrix.map { it[y] }.all { it.marked }
        return rowCompleted || columnCompleted
    }

    fun remainingValues() = matrix.fold(0) { acc, arrayList ->
        acc + arrayList.filter { !it.marked }.fold(0) { acc, int ->
            acc + int.number
        }
    }

    override fun toString(): String {
        return matrix.joinToString("\n")
    }
}