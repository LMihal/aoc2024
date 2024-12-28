import java.io.File

fun canAchieve(numbers: List<Long>, target: Long, index: Int, currentValue: Long): Boolean {

    if (index == numbers.size - 1) {
        return currentValue == target
    }

    val next = index + 1
    val nextNumber = numbers[next]

    if (canAchieve(numbers, target, next, currentValue + nextNumber)) {
        return true
    }

    if (canAchieve(numbers, target, next, currentValue * nextNumber)) {
        return true
    }

    if (canAchieve(numbers, target, next, "$currentValue$nextNumber".toLong())) {
        return true
    }

    return false
}

val input = File("test.txt").readLines()
var sum = 0L

for (row in input) {

    var vals = row.split(":")

    val target = vals[0].toLong()
    val numbers = vals[1].trim().split(" ").map(String::toLong)

    if (canAchieve(numbers, target, 0, numbers[0])) {
        sum += target
    }
    
}

println("$sum")


