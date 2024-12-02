import kotlin.math.abs

fun main() {
    val input = getResourceAsText("day2.txt")!!
    val levels: List<List<Int>> = input.trim()
        .split("\n")
        .map {
            it.trim().split(" ").map { i ->
                i.toInt()
            }
        }
    println(safetyCount(listOf(listOf(7,6,4,2,1), listOf(1,2,7,8,9), listOf(9,7,6,2,1), listOf(1,3,2,4,5), listOf(8,6,4,4,1), listOf(1,3,6,7,9))).toString() + " should be 2")
    println(safetyCount(levels))
    println(safetyRemoveCount(listOf(listOf(7,6,4,2,1), listOf(1,2,7,8,9), listOf(9,7,6,2,1), listOf(1,3,2,4,5), listOf(8,6,4,4,1), listOf(1,3,6,7,9))).toString() + " should be 2")
    println(safetyRemoveCount(levels))
    println(safetyRemoveCount(listOf(listOf(8,11,9,11,14))))
}

fun safetyCount(levels: List<List<Int>>): Int {
    return levels.sumOf { level ->
        val ascending = level[1] > level[0]
        var last = level[0]
        var output = 1
        for (i in 1 until level.size) {
            val v = level[i]
            if (abs(v - last) in 1..3 && (ascending xor (last > v))) {
                last = v
            } else {
                output = 0
                break
            }
        }
        output
    }
}

fun safetyRemoveCount(levels: List<List<Int>>): Int {
    return levels.sumOf {
        level -> if(safetyLevelTest(level, false) || safetyLevelTest(level.filterIndexed { index, _ -> index != 0 }, true) || safetyLevelTest(level.filterIndexed { index, _ -> index != 1 }, true)) 1L else 0L
    }.toInt()
}



fun safetyLevelTest(level: List<Int>, remove: Boolean): Boolean {
    val ascending = level.map { if (it > level[0]) 1 else 0 }.sum() > 1
    var last = level[0]
    var output = true
    var removed = remove
    for (i in 1 until level.size) {
        val v = level[i]
        if (abs(v - last) in 1..3 && (ascending xor (last > v))) {
            last = v
        } else if (!removed) {
            removed = true
        } else {
            output = false
            break
        }
    }
    return output
}