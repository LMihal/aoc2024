import java.io.File

val blinkDict = mutableMapOf<Pair<Long, Int>, Long>()

val input = File("input11.txt").readLines().flatMap { line ->
    line.split(" ").map { it.toLong() }
}

println(solve(input, 25))
println(solve(input, 75))

fun changeStone(stone: Long): List<Long> {
    val stoneStr = stone.toString()
    return when {
        stoneStr == "0" -> listOf(1L)
        stoneStr.length % 2 == 1 -> listOf(stone * 2024)
        else -> {
            val mid = stoneStr.length / 2
            listOf(
                stoneStr.substring(0, mid).toLong(),
                stoneStr.substring(mid).toLong()
            )
        }
    }
}

fun blink(stone: Long, iterations: Int): Long {
    val key = stone to iterations
    if (key in blinkDict) return blinkDict[key]!!

    val products = changeStone(stone)
    if (iterations == 0) {
        return products.size.toLong().also { blinkDict[key] = it }
    }

    val result = products.sumOf { blink(it, iterations - 1) }
    blinkDict[key] = result
    return result
}

fun solve(stones: List<Long>, iterations: Int): Long {
    return stones.sumOf { blink(it, iterations - 1) }
}
