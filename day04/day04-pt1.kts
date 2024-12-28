import java.io.File


fun searchRovno(input: List<String>): Int {

    val st = "XMAS"
    var sum = 0

    for (i in 0..<input.size) {
        sum += input[i].windowed(st.length).count { it == st }
        sum += input[i].windowed(st.length).count { it == st.reversed() }
    }

    return sum
}

fun rotate90(input: List<String>): List<String> {
    val rows = input.size
    val cols = input[0].length
    return (0 until cols).map { col ->
        (rows - 1 downTo 0).map { row -> input[row][col] }.joinToString("")
    }
}

fun getDiagonals(grid: List<String>): Int {

    val directions = listOf(
        Pair(-1, -1),
        Pair(1, 1),
        Pair(-1, 1),
        Pair(1, -1)
    )

    var sum = 0

    for (i in grid.indices) {
        for (j in grid[i].indices) {
            for (direction in directions) {
                if (checkDiagonal(grid, i, j, direction.first, direction.second, "XMAS")) {
                    sum++
                }
            }
        }
    }

    return sum
}

fun checkDiagonal(grid: List<String>, r: Int, c: Int, dr: Int, dc: Int, word: String): Boolean {

    for (k in word.indices) {
        if (r !in grid.indices || c !in grid[r].indices || grid[r][c] != word[k]) {
            return false
        }

        r += dr
        c += dc
    }

    return true
}

val input = File("input04.txt").readLines()

var sum = 0

sum += searchRovno(input) + searchRovno(rotate90(input)) + getDiagonals(input)
println(sum)










