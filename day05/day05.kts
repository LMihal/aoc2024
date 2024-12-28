import java.io.File

val (rulesList, updatesList) = File("input05.txt").readText().trim().split("\n\n")

val rules = rulesList.lines()
    .map { it.split("|").map(String::toInt).let { (a, b) -> a to b } }

val updates = updatesList.lines()
    .map { line -> line.split(",").map(String::toInt) }

fun isOrdered(update: List<Int>): Boolean {
    val map = update.withIndex().associate { it.value to it.index }
    return rules.all { (a, b) ->
        if (a in map && b in map) {
            map[a]!! < map[b]!!
        } else {
            true
        }
    }
}

fun orderPages(update: List<Int>): List<Int> {
    val graph = mutableMapOf<Int, MutableList<Int>>()
    val indegree = mutableMapOf<Int, Int>().withDefault { 0 }

    for ((a, b) in rules) {
        if (a in update && b in update) {
            graph.computeIfAbsent(a) { mutableListOf() }.add(b)
            indegree[b] = indegree.getValue(b) + 1
            indegree[a] = indegree.getValue(a)
        }
    }

    val sorted = mutableListOf<Int>()
    val queue = ArrayDeque(update.filter { indegree.getValue(it) == 0 })

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        sorted.add(current)
        for (neighbor in graph.getOrDefault(current, emptyList())) {
            indegree[neighbor] = indegree.getValue(neighbor) - 1
            if (indegree[neighbor] == 0) queue.addLast(neighbor)
        }
    }

    return sorted
}

val sum = updates.filterNot(::isOrdered).map { update ->
    val ordered = orderPages(update)
    ordered[ordered.size / 2]
}.sum()

println("$sum")
