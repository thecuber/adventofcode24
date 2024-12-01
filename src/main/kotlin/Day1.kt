import kotlin.math.abs

fun getResourceAsText(path: String): String? =
    object {}.javaClass.getResource(path)?.readText()

fun main() {
    val input = getResourceAsText("day1.txt")!!
    val leftList: MutableList<Int> = mutableListOf()
    val rightList: MutableList<Int> = mutableListOf()
    for(line in input.trim().split("\n")) {
        val split = line.split("   ")
        leftList.add(split[0].toInt())
        rightList.add(split[1].toInt())
    }
    println("TEST: " + distanceScore(listOf(3,4,2,1,3,3), listOf(4,3,5,3,9,3)).toString() +  " should be 11")
    println(distanceScore(leftList, rightList))
    println("TEST: " + similarityScore(listOf(3,4,2,1,3,3), listOf(4,3,5,3,9,3)).toString() +  " should be 31")
    println(similarityScore(leftList, rightList))
}


fun distanceScore(left: List<Int>, right: List<Int>): Int {
    val leftList = left.toMutableList()
    val rightList = right.toMutableList()
    leftList.sort()
    rightList.sort()
    val size = left.size
    val sumList = List(size) {
        abs(leftList[it] - rightList[it])
    }
    val result = sumList.sum()
    return result
}

fun similarityScore(left: List<Int>, right: List<Int>): Int {
    val leftMap: MutableMap<Int, Int> = mutableMapOf()
    val rightMap: MutableMap<Int, Int> = mutableMapOf()
    for(elt in left) {
        leftMap[elt] = leftMap.getOrDefault(elt, 0) + 1
    }
    for(elt in right) {
        rightMap[elt] = rightMap.getOrDefault(elt, 0) + 1
    }
    val resultList = leftMap.keys.map {
        it * leftMap[it]!! * rightMap.getOrDefault(it, 0)
    }
    return resultList.sum()
}
