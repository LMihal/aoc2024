import java.io.File


val map = File("input06.txt").readLines().map { it.toCharArray().toMutableList() }

val rows = map.size
val cols = map[0].size

val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
var currentDirection = 0

var guardX = 0
var guardY = 0
for (r in 0 until rows) {
    for (c in 0 until cols) {
        if (map[r][c] in "^>v<") {
            guardX = r
            guardY = c
            currentDirection = when (map[r][c]) {
                '^' -> 0
                '>' -> 1
                'v' -> 2
                '<' -> 3
                else -> 0
            }
            break
        }
    }
}

val visited = mutableSetOf(Pair(guardX, guardY))

while (true) {
    val (dx, dy) = directions[currentDirection]
    val nextX = guardX + dx
    val nextY = guardY + dy

    if (nextX !in 0 until rows || nextY !in 0 until cols) break

    if (map[nextX][nextY] != '#') {
        guardX = nextX
        guardY = nextY
        visited.add(Pair(guardX, guardY))
    } else {
        currentDirection = (currentDirection + 1) % 4
    }
}

println("${visited.size}")
