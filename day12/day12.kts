import java.io.File


val input = File("input12.txt").readLines()

val grid = mutableMapOf<Pair<Int, Int>, Char>()
input.forEachIndexed { i, row ->
    row.trim().forEachIndexed { j, c ->
        grid[Pair(i, j)] = c
    }
}

val sets = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()
for (p in grid.keys) {
    sets[p] = mutableSetOf(p)
}

for (p in grid.keys) {
    val neighbors = listOf(
        Pair(p.first + 1, p.second),
        Pair(p.first - 1, p.second),
        Pair(p.first, p.second + 1),
        Pair(p.first, p.second - 1)
    )
    for (n in neighbors) {
        if (n in grid && grid[p] == grid[n]) {
            sets[p]?.addAll(sets[n] ?: emptySet())
            sets[p]?.forEach { x ->
                sets[x] = sets[p] ?: mutableSetOf()
            }
        }
    }
}

val uniqueSets = sets.values.map { it.toSet() }.toSet()

fun edge(ps: Set<Pair<Int, Int>>): Pair<Set<Pair<Pair<Int, Int>, Pair<Int, Int>>>, Set<Pair<Pair<Int, Int>, Pair<Int, Int>>>> {
    val directions = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))
    val edges = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    ps.forEach { p ->
        directions.forEach { d ->
            val neighbor = Pair(p.first + d.first, p.second + d.second)
            if (neighbor !in ps) {
                edges.add(Pair(p, d))
            }
        }
    }

    val secondPart = edges.filterNot { (p, d) ->
        val neighbor = Pair(p.first + d.second, p.second - d.first)
        Pair(neighbor, d) in edges
    }.toSet()

    return Pair(edges, secondPart)
}

for (part in 0..1) {
    val result = uniqueSets.sumOf { s ->
        val (allEdges, secondPartEdges) = edge(s)
        if (part == 0) allEdges.size * s.size else secondPartEdges.size * s.size
    }
    println(result)
}
