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

val startX = guardX
val startY = guardY

val visited = mutableSetOf(Pair(guardX, guardY))
val path = mutableListOf<Pair<Int, Int>>()

while (true) {
    val (dx, dy) = directions[currentDirection]
    val nextX = guardX + dx
    val nextY = guardY + dy

    if (nextX !in 0 until rows || nextY !in 0 until cols) break

    if (map[nextX][nextY] != '#') {
        guardX = nextX
        guardY = nextY
        visited.add(Pair(guardX, guardY))
        path.add(Pair(guardX, guardY))
    } else {
        currentDirection = (currentDirection + 1) % 4
    }
}

fun causesLoop(addedObstacle: Pair<Int, Int>): Boolean {
    val newVisited = mutableSetOf<Pair<Int, Int>>()
    val newPath = mutableListOf<Pair<Int, Int>>()
    var x = startX
    var y = startY
    var direction = 0

    val (obstacleX, obstacleY) = addedObstacle
    map[obstacleX][obstacleY] = '#'

    while (true) {
        var (dx, dy) = directions[direction]
        var nextX = x + dx
        var nextY = y + dy

        if (nextX !in 0 until rows || nextY !in 0 until cols) {
            map[obstacleX][obstacleY] = '.'
            return false
        }

        if (map[nextX][nextY] != '#') {
            x = nextX
            y = nextY
            newPath.add(Pair(x, y))

            if (path.size*5 < newPath.size && Pair(x, y) in newVisited) {
                map[obstacleX][obstacleY] = '.'
                return true
            }

            newVisited.add(Pair(x, y))

        } else {
            direction = (direction + 1) % 4
        }
    }
}


val possibleObstacles = visited.filter { (x, y) ->
    map[x][y] == '.' && (x != startX || y != startY) && causesLoop(Pair(x, y))
}

println("${visited.size}")
println("${possibleObstacles.size}")
