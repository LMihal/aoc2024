import java.io.File

val map = File("input10.txt").readLines().map { line ->
    line.map { it.digitToInt() }
}

val trailheads = findTrailheads(map)
val totalScore = trailheads.sumOf { scoreTrailhead(it, map) }

println("$totalScore")

data class Position(val x: Int, val y: Int)

fun findTrailheads(map: List<List<Int>>): List<Position> {
    val trailheads = mutableListOf<Position>()
    for (y in map.indices) {
        for (x in map[y].indices) {
            if (map[y][x] == 0) {
                trailheads.add(Position(x, y))
            }
        }
    }
    return trailheads
}

fun scoreTrailhead(start: Position, map: List<List<Int>>): Int {
    val directions = listOf(
        Position(0, -1),
        Position(0, 1),
        Position(-1, 0),
        Position(1, 0)
    )

    fun dfs(current: Position, currentHeight: Int): Int {
        val currentValue = map.getOrNull(current.y)?.getOrNull(current.x) ?: return 0
        if (currentValue != currentHeight) return 0

        if (currentValue == 9) return 1

        return directions.sumOf { dir ->
            dfs(Position(current.x + dir.x, current.y + dir.y), currentHeight + 1)
        }
    }

    return dfs(start, 0)
}
