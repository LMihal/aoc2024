import java.io.File


val input = File("input09.txt").readText().trim()
val blocks = parseDiskMap(input)

compactDisk(blocks)

val checksum = calculateChecksum(blocks)

println("$checksum")

fun parseDiskMap(diskMap: String): MutableList<String> {
    val blocks = mutableListOf<String>()
    var fileId = 0

    var i = 0
    while (i < diskMap.length) {
        val fileSize = diskMap[i].digitToInt()
        i++
        val freeSpace = if (i < diskMap.length) diskMap[i].digitToInt() else 0
        i++
        repeat(fileSize) { blocks.add(fileId.toString()) }

        fileId++

        repeat(freeSpace) { blocks.add(".") }
    }
    return blocks
}

fun compactDisk(blocks: MutableList<String>) {
    var writeIndex = 0

    for (readIndex in blocks.size - 1 downTo 0) {

        while (blocks[writeIndex] != ".") {
            writeIndex++
        }

        if (readIndex <= writeIndex) {
            println("$writeIndex, $readIndex")
            return
        }

        if (blocks[readIndex] != ".") {
            if (readIndex != writeIndex) {
                blocks[writeIndex] = blocks[readIndex]
                blocks[readIndex] = "."
            }
        }
    }
}

fun calculateChecksum(blocks: List<String>): Long {
    var checksum = 0L
    for (index in blocks.indices) {
        if (blocks[index] != ".") {
            checksum += index * blocks[index].toLong()
        }
    }
    return checksum
}
