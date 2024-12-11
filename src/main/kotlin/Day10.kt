fun main() {
    val test1 = "...0...\n" +
            "...1...\n" +
            "...2...\n" +
            "6543456\n" +
            "7.....7\n" +
            "8.....8\n" +
            "9.....9"
    println(trailheadScores(process(test1)))
    val test2 = "..90..9\n" +
            "...1.98\n" +
            "...2..7\n" +
            "6543456\n" +
            "765.987\n" +
            "876....\n" +
            "987...."
    println(trailheadScores(process(test2)))
    val test3 = "89010123\n" +
            "78121874\n" +
            "87430965\n" +
            "96549874\n" +
            "45678903\n" +
            "32019012\n" +
            "01329801\n" +
            "10456732"
    println(trailheadScores(process(test3)))
    println("True result ${trailheadScores(process(getResourceAsText("day10.txt")!!))}")
    println("${trailheadRating(process(test2))} should be 13")
    println("${trailheadRating(process("012345\n" +
            "123456\n" +
            "234567\n" +
            "345678\n" +
            "4.6789\n" +
            "56789."))} should be 227")
    println("${trailheadRating(process(test3))} should be 81")
    println(trailheadRating(process(getResourceAsText("day10.txt")!!)))
}

private fun process(input: String): List<List<Int>> = input.trim().split("\n").map {
    it.trim().map { c ->
        if(c == '.') -1 else c.digitToInt()
    }
}

fun trailheadScores(input: List<List<Int>>): Int {
    return input.mapIndexed { i, it ->
        var count = 0
        for(j in it.indices) {
            if(it[j] == 0) {
                val list = mutableListOf(Triple(i, j, 0))
                val out: MutableList<Pair<Int, Int>> = mutableListOf()
                while(list.size > 0) {
                    val (a, b, c) = list.removeFirst()
                    if(a > 0 && input[a - 1][b] == c + 1) {
                        if(c == 8) {
                            if(Pair(a - 1, b) !in out) {
                                out.add(Pair(a-1, b))
                            }
                        } else {
                            list.add(Triple(a-1, b, c +1))
                        }
                    }
                    if(b > 0 && input[a][b - 1] == c + 1) {
                        if(c == 8) {
                            if(Pair(a, b - 1) !in out) {
                                out.add(Pair(a, b - 1))
                            }
                        } else {
                            list.add(Triple(a, b - 1, c +1))
                        }
                    }
                    if(a < input.size - 1 && input[a + 1][b] == c + 1) {
                        if(c == 8) {
                            if(Pair(a + 1, b) !in out) {
                                out.add(Pair(a+1, b))
                            }
                        } else {
                            list.add(Triple(a+1, b, c +1))
                        }
                    }
                    if(b < input[0].size - 1 && input[a][b + 1] == c + 1) {
                        if(c == 8) {
                            if(Pair(a, b + 1) !in out) {
                                out.add(Pair(a, b + 1))
                            }
                        } else {
                            list.add(Triple(a, b + 1, c +1))
                        }
                    }
                }
                count += out.size
            }
        }
        count
    }.sum()
}

fun trailheadRating(input: List<List<Int>>): Int {
    return input.mapIndexed { i, it ->
        var count = 0
        for(j in it.indices) {
            if(it[j] == 0) {
                val list = mutableListOf(Triple(i, j, 0))
                var innerCount = 1
                while(list.size > 0) {
                    var s = list.size
                    val (a, b, c) = list.removeFirst()
                    if(a > 0 && input[a - 1][b] == c + 1) {
                        if(c == 8) {
                            s -= 1
                        } else {
                            list.add(Triple(a-1, b, c +1))
                        }
                    }
                    if(b > 0 && input[a][b - 1] == c + 1) {
                        if(c == 8) {
                            s -= 1
                        } else {
                            list.add(Triple(a, b - 1, c +1))
                        }
                    }
                    if(a < input.size - 1 && input[a + 1][b] == c + 1) {
                        if(c == 8) {
                            s -= 1
                        } else {
                            list.add(Triple(a+1, b, c +1))
                        }
                    }
                    if(b < input[0].size - 1 && input[a][b + 1] == c + 1) {
                        if(c == 8) {
                            s -= 1
                        } else {
                            list.add(Triple(a, b + 1, c +1))
                        }
                    }
                    innerCount += list.size - s
                }
                count += innerCount
            }
        }
        count
    }.sum()
}