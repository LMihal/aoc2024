import java.io.File


data class ClawMachine(
    val ax: Long, val ay: Long,
    val bx: Long, val by: Long,
    val cx: Long, val cy: Long
)

fun parseMachinesFromFile(fileName: String): List<ClawMachine> {
    val lines = File(fileName).readLines().map { it.trim() }
    val machines = mutableListOf<ClawMachine>()

    var i = 0
    while (i < lines.size) {
        if (lines[i].isBlank()) {
            i++
            continue
        }

        val al = lines[i++].substringAfter("Button A: ").split(", ").map { it.substring(2).toLong() }
        val bl = lines[i++].substringAfter("Button B: ").split(", ").map { it.substring(2).toLong() }
        val cl = lines[i++].substringAfter("Prize: ").split(", ").map { it.substring(2).toLong() }

        machines.add(
            ClawMachine(
                ax = al[0], ay = al[1],
                bx = bl[0], by = bl[1],
                cx = cl[0], cy = cl[1]
            )
        )
    }

    return machines
}

fun minTokensToWinPrizes(machines: List<ClawMachine>): Long {
    var totalCost = 0L

    for (machine in machines) {

        val (ax, ay, bx, by, x, y) = machine

        var minCost = Long.MAX_VALUE
        var foundSolution = false

        val a = (x * by - y * bx) / (ax * by - ay * bx)
        val b = (x - ax * a) / bx
        totalCost +=  if (a * ax + b * bx == x && a * ay + b * by == y) 3 * a + b else 0
    }

    return totalCost
}


val machines = parseMachinesFromFile("input13.txt")
val totalCost = minTokensToWinPrizes(machines)

println("$totalCost")

