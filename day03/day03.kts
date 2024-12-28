import java.io.File


val input = File("input03.txt").readLines()

var sum = 0.0
var ena = true

for (row in input) {
    val result = Regex("""mul\((-?\d+(\.\d+)?),\s*(-?\d+(\.\d+)?)\)|do\(\)|don't\(\)""").findAll(row)

    var internalSum = 0.0

    if (result.any()) {
        result.forEach { match ->
            when {
                match.value.startsWith("mul") && ena -> {
                    val number1 = match.groups[1]?.value?.toDoubleOrNull() ?: 0.0
                    val number2 = match.groups[3]?.value?.toDoubleOrNull() ?: 0.0

                    internalSum += number1 * number2
                }

                match.value == "do()" -> {
                    ena = true
                }

                match.value == "don't()" -> {
                    ena = false
                }
            }
        }
    }

    sum += internalSum
}

println("${sum.toInt()}")

