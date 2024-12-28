import java.io.File
import kotlin.math.max

val input = File("input09.txt").readText().trim()
var fileId = 0

val blocks = parseDiskMap(input)
compactDisk(blocks)
val checksum = calculateChecksum(blocks)

println("$checksum")

fun parseDiskMap(diskMap: String): MutableList<String> {
    val blocks = mutableListOf<String>()

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
    var readIndex = blocks.size-1

    var target = fileId-1

    for (nn in input.indices) {

        val file = getFileBlock(blocks, target, readIndex)

        while (writeIndex < readIndex && writeIndex < blocks.size) {
            val space = getFreeBlock(blocks, writeIndex)
            val fileSize = file.second - file.first
            val spaceSize = space.second - space.first

            if (readIndex <= space.first || readIndex <= space.second) {
                readIndex = file.first
                break
            }

            if (fileSize <= spaceSize) {

                for (i in 0..<fileSize) {
                    blocks[space.first + i] = blocks[file.second-i-1]
                    blocks[file.second-i-1] = "."
                }

                readIndex = file.first
                break
            }

            writeIndex += spaceSize

        }

        writeIndex = 0
        target--
        if (target < 0) return
        if (target%1000 == 0) println(target)

    }
}


fun getFreeBlock(blocks: MutableList<String>, from: Int): Pair<Int, Int> {
    var first = from
    while (first < blocks.size && blocks[first] != ".") {
        first++
    }
    if (first >= blocks.size) return Pair(0, 0)

    var end = first
    while (end < blocks.size && blocks[end] == ".") {
        end++
    }

    return Pair(first, end)
}

fun getFileBlock(blocks: MutableList<String>, target: Int, from: Int): Pair<Int, Int> {
    val targetString = target.toString()
    var startIndex = -1
    var endIndex = -1

    for (i in from downTo 0) {
        var value = blocks[i]
        if (value == targetString) {
            if (endIndex == -1) endIndex = i
            startIndex = i
        }
    }

    return if (startIndex != -1 && endIndex != -1) Pair(startIndex, endIndex+1) else Pair(0, 0)
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
