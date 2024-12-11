fun main() {
    println(stonesCount("0 1 10 99 999", 1))
    println(stonesCount("125 17", 6))
    println(stonesCount(getResourceAsText("day11.txt")!!))
    println(stonesCount(getResourceAsText("day11.txt")!!, 25))
    println(stonesCount(getResourceAsText("day11.txt")!!, 75))
}


private fun stonesCount(input: String, totalBlink: Int = 25): Long {
    var map = input.trim().split(" ").associate { Pair(it.toLong(), 1L) }.toMutableMap()
    //println(map)
    for(i in 0..<totalBlink) {
        val cpMap: MutableMap<Long, Long> = mutableMapOf()
        for(key in map.keys) {
            val length = key.toString().length
            //println("$key $length")
            if(key == 0L) {
                cpMap[1L] = cpMap.getOrDefault(1L, 0L) + map[key]!!
            } else if(length % 2 == 0) {
                val left = key.toString().substring(0, length / 2).toLong()
                val right = key.toString().substring(length / 2).toLong()
                cpMap[left] = cpMap.getOrDefault(left, 0L) + map[key]!!
                cpMap[right] = cpMap.getOrDefault(right, 0L) + map[key]!!
            } else {
                cpMap[2024 * key] = cpMap.getOrDefault(2024L * key, 0L) + map[key]!!
            }
            //println(cpMap)
        }
        map = cpMap
    }
    //println(map)
    return map.values.sum()
}