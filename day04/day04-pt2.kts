import java.io.File

fun getDiagonals(grid: List<String>): Int {

    var sum = 0

    for (i in 1..<grid.size-1) {

        for (j in 1..<grid[i].length-1) {

            if (
                grid[i][j] == 'A' &&
                (
                    (grid[i-1][j-1] == 'M' && grid[i-1][j+1] == 'S' &&
                    grid[i+1][j-1] == 'M' && grid[i+1][j+1] == 'S') ||

                    (grid[i-1][j-1] == 'M' && grid[i-1][j+1] == 'M' &&
                    grid[i+1][j-1] == 'S' && grid[i+1][j+1] == 'S') ||

                    (grid[i-1][j-1] == 'S' && grid[i-1][j+1] == 'S' &&
                    grid[i+1][j-1] == 'M' && grid[i+1][j+1] == 'M') ||

                    (grid[i-1][j-1] == 'S' && grid[i-1][j+1] == 'M' &&
                    grid[i+1][j-1] == 'S' && grid[i+1][j+1] == 'M')

                )
            ) sum++

        }

    }

    return sum
}

val input = File("input04.txt").readLines()
println(getDiagonals(input))
