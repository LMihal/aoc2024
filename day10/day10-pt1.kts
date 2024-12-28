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
    val visited = mutableSetOf<Position>()
    val queue = ArrayDeque<Position>()
    queue.add(start)

    val directions = listOf(
        Position(0, -1),
        Position(0, 1),
        Position(-1, 0),
        Position(1, 0)
    )

    val reachableNines = mutableSetOf<Position>()

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()

        if (current in visited) continue
        visited.add(current)

        val currentHeight = map.getOrNull(current.y)?.getOrNull(current.x) ?: continue

        if (currentHeight == 9) {
            reachableNines.add(current)
            continue
        }

        directions.forEach { dir ->
            val next = Position(current.x + dir.x, current.y + dir.y)
            val nextHeight = map.getOrNull(next.y)?.getOrNull(next.x) ?: -1
            if (next !in visited && nextHeight == currentHeight + 1) {
                queue.add(next)
            }
        }
    }

    return reachableNines.size
}
