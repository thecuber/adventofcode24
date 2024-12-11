import kotlin.math.pow

fun main() {
    println(calibrationSum(process("190: 10 19\n" +
            "3267: 81 40 27\n" +
            "83: 17 5\n" +
            "156: 15 6\n" +
            "7290: 6 8 6 15\n" +
            "161011: 16 10 13\n" +
            "192: 17 8 14\n" +
            "21037: 9 7 18 13\n" +
            "292: 11 6 16 20")))
    println(calibrationSum(process(getResourceAsText("day7.txt")!!)))
    //println(calibrationSum(listOf(Pair(292, listOf(11, 6, 16, 20)))))
    println(calibrationMoveSum(process("190: 10 19\n" +
            "3267: 81 40 27\n" +
            "83: 17 5\n" +
            "156: 15 6\n" +
            "7290: 6 8 6 15\n" +
            "161011: 16 10 13\n" +
            "192: 17 8 14\n" +
            "21037: 9 7 18 13\n" +
            "292: 11 6 16 20")))
    println(calibrationMoveSum(process(getResourceAsText("day7.txt")!!)))
}

private fun process(input: String): List<Pair<Long, List<Long>>> = input.trim().split("\n").map { b ->
    val a = b.trim().split(": ")
    Pair(a[0].toLong(), a[1].trim().split(" ").map { it.toLong() })
}

fun calibrationSum(input: List<Pair<Long, List<Long>>>): Long {
    return input.sumOf { (test, values) ->
        var found = false
        for(i in 0..< 2.0.pow((values.size - 1).toDouble()).toLong()) {
            val operators = List(values.size - 1) {
                (i / (2.0.pow(it).toInt())) % 2
            }
            var total = values[0]
            for(j in 0..<(values.size - 1)) {
                if(operators[j] == 0L) {
                    total += values[j + 1]
                } else {
                    total *= values[j + 1]
                }
            }
            if(total == test) {
                found = true
                break
            }
        }
        if(found) test else 0L
    }
}


fun calibrationMoveSum(input: List<Pair<Long, List<Long>>>): Long {
    return input.sumOf { (test, values) ->
        var found = false
        for(i in 0..< 3.0.pow((values.size - 1).toDouble()).toLong()) {
            val operators = List(values.size - 1) {
                (i / (3.0.pow(it).toInt())) % 3
            }
            var total = values[0]
            for(j in 0..<(values.size - 1)) {
                if(operators[j] == 0L) {
                    total += values[j + 1]
                } else if(operators[j] == 1L) {
                    total *= values[j + 1]
                } else {
                    total = total * 10.0.pow(values[j+1].toString().length.toDouble()).toLong() + values[j+1]
                }
                if(total > test) {
                    break
                }
            }
            if(total == test) {
                found = true
                break
            }
        }
        if(found) test else 0L
    }
}