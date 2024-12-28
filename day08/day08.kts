import java.io.File


val input = File("input08.txt").readLines()

val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
for (y in input.indices) {
    for (x in input[y].indices) {
        val char = input[y][x]
        if (char != '.') {
            antennas.computeIfAbsent(char) { mutableListOf() }.add(x to y)
        }
    }
}

println(antennas)

val antinodes = mutableSetOf<Pair<Int, Int>>()

for ((frequency, positions) in antennas) {
    for (i in positions.indices) {
        for (j in i + 1 until positions.size) {
            val (x1, y1) = positions[i]
            val (x2, y2) = positions[j]

            var dx = x2 - x1
            var dy = y2 - y1

            val delitel = nsd(dx, dy)
            dx /= delitel
            dy /= delitel

            var x = x1
            var y = y1

            while (x in input[0].indices && y in input.indices) {
                antinodes.add(x to y)
                x += dx
                y += dy
            }

            x = x1 - dx
            y = y1 - dy
            while (x in input[0].indices && y in input.indices) {
                antinodes.add(x to y)
                x -= dx
                y -= dy
            }

        }
    }
}

val uniqueAntinodes = antinodes.filter { (x, y) ->
    x in input[0].indices && y in input.indices
}

println("${uniqueAntinodes.size}")

fun nsd(a: Int, b: Int): Int {
    if (b == 0) return kotlin.math.abs(a)
    return nsd(b, a % b)
}
