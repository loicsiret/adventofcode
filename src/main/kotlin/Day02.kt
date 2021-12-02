fun main() {
    fun part1(input: List<String>) = input.asSequence()
        .map { it.split(" ") }
        .map { Direction::direction.find(it[0])?.let { it1 -> Command(it1, it[1].toInt()) } }
        .filterNotNull()
        .fold(Position(0, 0)) { acc, command -> acc move command }
        .let { it.depthPos * it.horizontalPos }


    fun part2(input: List<String>) = input.asSequence()
        .map { it.split(" ") }
        .map { Direction::direction.find(it[0])?.let { it1 -> Command(it1, it[1].toInt()) } }
        .filterNotNull()
        .fold(Position(0, 0)) { acc, command -> acc moveWithAim command }
        .let { it.depthPos * it.horizontalPos }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

data class Position(val depthPos: Int, val horizontalPos: Int, val aim: Int = 0) {
    infix fun move(command: Command): Position {
        return when (command.direction) {
            Direction.Forward -> Position(depthPos, horizontalPos + command.unit)
            else -> Position(
                depthPos + command.direction.operation * command.unit,
                horizontalPos
            )
        }
    }

    infix fun moveWithAim(command: Command): Position {
        return when (command.direction) {
            Direction.Forward -> Position(
                depthPos + command.unit * aim,
                horizontalPos + command.unit,
                aim
            )
            else -> Position(
                depthPos,
                horizontalPos,
                aim + command.direction.operation * command.unit
            )
        }
    }
}

data class Command(val direction: Direction, val unit: Int)
enum class Direction(val direction: String, val operation: Int) {
    Forward("forward", 1),
    Down("down", 1),
    Up("up", -1),
}

inline fun <reified T : Enum<T>, V> ((T) -> V).find(value: V): T? {
    return enumValues<T>().firstOrNull { this(it) == value }
}
