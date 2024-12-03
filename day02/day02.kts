import java.io.File
import kotlin.math.abs

fun isSafe(vals: List<Int>): Boolean {
    return vals.zipWithNext().all { (a, b) -> (a < b) && (1 <= abs(a-b) && abs(a-b) <= 3) } ||
            vals.zipWithNext().all { (a, b) -> (a > b) && (1 <= abs(a-b) && abs(a-b) <= 3) }
}

val input = File("input02.txt").readLines()

var count = 0

for (row in input) {
    val vals = row.trim().split(" ").map { it.toInt() }

    for (i in 0..<vals.size) {
        var valsCpy = vals.filterIndexed { index, v -> index != i }

        if (isSafe(valsCpy)) {
            count++
            break
        }
    }

}

println(count)
