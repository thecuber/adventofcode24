fun main() {
    val input = getResourceAsText("day6.txt")!!
    val test = "....#.....\n" +
            ".........#\n" +
            "..........\n" +
            "..#.......\n" +
            ".......#..\n" +
            "..........\n" +
            ".#..^.....\n" +
            "........#.\n" +
            "#.........\n" +
            "......#..."
    println(distinctPos(test))
    println(distinctPos(input))
    println(obstructionCount(test))
    println(obstructionCount(input))
}

fun distinctPos(input: String): Int {
    val obstacles: MutableList<Pair<Int, Int>> = mutableListOf()
    var pos: Pair<Int, Int> = Pair(0,0)
    input.trim().split("\n").forEachIndexed { index, s ->
        "#".toRegex().findAll(s.trim()).forEach {
            obstacles.add(Pair(it.range.first, index))
        }
        val start = "\\^".toRegex().find(s.trim())
        if(start != null) {
            pos = Pair(start.range.first, index)
        }
    }
    val width = input.trim().split("\n")[0].length
    val height = input.trim().split("\n").size
    var direction = 0//0: Up, 1: Right, 2: Down, 3: Left
    val visited: MutableList<Pair<Int, Int>> = mutableListOf(pos)
    while(true) {
        val nextPlace = when(direction) {
            0 -> Pair(pos.first, pos.second - 1 )
            1 -> Pair(pos.first + 1, pos.second)
            2 -> Pair(pos.first, pos.second + 1)
            3 -> Pair(pos.first - 1, pos.second)
            else -> Pair(-1, -1)
        }
        if(!(nextPlace.first in 0..<width && nextPlace.second in 0..<height)) {
            break
        }
        if(obstacles.contains(nextPlace)) {
            direction = (direction + 1) % 4
        } else {
            pos = nextPlace
            if(!(visited.contains(pos)))
                visited.add(pos)
        }
    }
    return visited.count()
}

fun obstructionCount(input: String): Int {
    val obstacles: MutableList<Pair<Int, Int>> = mutableListOf()
    var pos: Triple<Int, Int, Int> = Triple(0, 0, 0)
    input.trim().split("\n").forEachIndexed { index, s ->
        "#".toRegex().findAll(s.trim()).forEach {
            obstacles.add(Pair(it.range.first, index))
        }
        val start = "\\^".toRegex().find(s.trim())
        if(start != null) {
            pos = Triple(start.range.first, index, 0)
        }
    }
    val width = input.trim().split("\n")[0].length
    val height = input.trim().split("\n").size
    var direction = 0//0: Up, 1: Right, 2: Down, 3: Left
    val visited: MutableList<Triple<Int, Int, Int>> = mutableListOf(pos)
    while(true) {
        val nextPlace = when(direction) {
            0 -> Pair(pos.first, pos.second - 1 )
            1 -> Pair(pos.first + 1, pos.second)
            2 -> Pair(pos.first, pos.second + 1)
            3 -> Pair(pos.first - 1, pos.second)
            else -> Pair(-1, -1)
        }
        if(!(nextPlace.first in 0..<width && nextPlace.second in 0..<height)) {
            break
        }
        if(obstacles.contains(nextPlace)) {
            direction = (direction + 1) % 4
        } else {
            pos = Triple(nextPlace.first, nextPlace.second, direction)
            if(!(visited.contains(pos)))
                visited.add(pos)
        }
    }
    var count = 0
    for(i in 0..<width) {
        for(j in 0..<height) {
            val find = visited.find {it.first == i && it.second == j }
            if(find == null) {
                continue
            }
            val obstaclesStar: MutableList<Pair<Int, Int>> = mutableListOf()
            obstaclesStar.addAll(obstacles)
            obstaclesStar.add(Pair(i, j))
            val previousPos = when((find.third + 2) % 4) {
                0 -> Pair(find.first, find.second - 1 )
                1 -> Pair(find.first + 1, find.second)
                2 -> Pair(find.first, find.second + 1)
                3 -> Pair(find.first - 1, find.second)
                else -> Pair(-1, -1)
            }
            var posStar = Triple(
                previousPos.first,
                previousPos.second,
                (find.third + 1) % 4
            )
            val visitedStar: MutableList<Triple<Int, Int, Int>> = mutableListOf(posStar)
            var looped = false
            while(true) {
                val nextPlace = when(posStar.third) {
                    0 -> Pair(posStar.first, posStar.second - 1 )
                    1 -> Pair(posStar.first + 1, posStar.second)
                    2 -> Pair(posStar.first, posStar.second + 1)
                    3 -> Pair(posStar.first - 1, posStar.second)
                    else -> Pair(-1, -1)
                }
                if(!(nextPlace.first in 0..<width && nextPlace.second in 0..<height)) {
                    break
                }
                if(obstaclesStar.contains(nextPlace)) {
                    posStar = Triple(posStar.first, posStar.second, (posStar.third + 1) % 4)
                } else {
                    posStar = Triple(nextPlace.first, nextPlace.second, posStar.third)
                    if(!(visitedStar.contains(posStar)))
                        visitedStar.add(posStar)
                    else {
                        looped = true
                        break
                    }
                }
            }
            if(looped)
                count++
        }
    }
    return count
}