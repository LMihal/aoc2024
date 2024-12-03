import java.io.File
import kotlin.math.abs

val input = File("input01.txt").readLines()
var left = mutableListOf<Int>()
var right = mutableListOf<Int>()

for (row in input) {
    val sp = row.split("   ")
    left += sp[0].toInt()
    right += sp[1].toInt()
}

left.sort()
right.sort()

var sum = 0

for (i in 0..<left.size) {

    sum += abs(left[i] - right[i])
}

println(sum)

var mapa = right.groupingBy{ it }.eachCount()

sum = 0

for (v in left) {
    sum += v * mapa.getOrDefault(v, 0)
}

println(sum)
