fun main() {
    println(antiNodeLocations(process("............\n" +
            "........0...\n" +
            ".....0......\n" +
            ".......0....\n" +
            "....0.......\n" +
            "......A.....\n" +
            "............\n" +
            "............\n" +
            "........A...\n" +
            ".........A..\n" +
            "............\n" +
            "............")))
    println(antiNodeLocations(process(getResourceAsText("day8.txt")!!)))
    println(antiNodePlusLocations(process("............\n" +
            "........0...\n" +
            ".....0......\n" +
            ".......0....\n" +
            "....0.......\n" +
            "......A.....\n" +
            "............\n" +
            "............\n" +
            "........A...\n" +
            ".........A..\n" +
            "............\n" +
            "............")))
    println(antiNodePlusLocations(process(getResourceAsText("day8.txt")!!)))
}

private fun process(input: String): Triple<Map<Char, List<Pair<Int, Int>>>, Int, Int> {
    val map: MutableMap<Char, List<Pair<Int, Int>>> = mutableMapOf()
    input.trim().split("\n").forEachIndexed { i, s ->
        s.trim().toCharArray().forEachIndexed { j, c ->
            if(c != '.') {
                val l = map.getOrDefault(c, listOf()).toMutableList()
                l.add(Pair(i + 1, j + 1))
                map[c] = l
            }
        }
    }
    return Triple(map, input.trim().split("\n").size, input.trim().split("\n")[0].trim().length)
}

private fun antiNodeLocations(input: Triple<Map<Char, List<Pair<Int, Int>>>, Int, Int>): Int {
    val map = input.first
    val width = input.second
    val height = input.third
    val antiNodes: MutableList<Pair<Int, Int>> = mutableListOf()
    map.forEach { (_, pairs) ->
        for(i in pairs.indices) {
            for(j in pairs.indices) {
                if(i == j) {
                    continue
                }
                val iNode = pairs[i]
                val jNode = pairs[j]
                val sNode = Pair(2*iNode.first - jNode.first, 2*iNode.second - jNode.second)
                if(sNode.first in 1..width && sNode.second in 1..height && !antiNodes.contains(sNode)) {
                    antiNodes.add(sNode)
                }
            }
        }
    }

    return antiNodes.size
}

private fun antiNodePlusLocations(input: Triple<Map<Char, List<Pair<Int, Int>>>, Int, Int>): Int {
    val map = input.first
    val width = input.second
    val height = input.third
    val antiNodes: MutableList<Pair<Int, Int>> = mutableListOf()
    map.forEach {
        (_, i) -> antiNodes.addAll(i)
    }
    map.forEach { (_, pairs) ->
        for(i in pairs.indices) {
            for(j in pairs.indices) {
                if(i == j) {
                    continue
                }
                val iNode = pairs[i]
                val jNode = pairs[j]
                val diff = Pair(iNode.first - jNode.first, iNode.second - jNode.second)
                var count = 1
                while(true) {
                    val sNode = Pair(iNode.first + count * diff.first, iNode.second + count * diff.second)
                    if(!(sNode.first in 1..width && sNode.second in 1..height)) {
                        break
                    }
                    if(!antiNodes.contains(sNode)) {
                        antiNodes.add(sNode)
                    }
                    count++
                }
            }
        }
    }

    return antiNodes.size
}