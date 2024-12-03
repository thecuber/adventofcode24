fun main() {
    val input = getResourceAsText("day3.txt")!!
    val testInput = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    println(uncorruptedMulSearch(testInput).toString() + " should be 161")
    println(uncorruptedMulSearch(input))
    println(enabledMulSearch("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))").toString() + " should be 48")
    println(enabledMulSearch(input))
}

fun uncorruptedMulSearch(input: String): Int {
    val regex = "mul\\(\\d+,\\d+\\)".toRegex()
    return regex.findAll(input).map {
        val str = it.value.substring(4, it.value.length - 1).split(",")
        str[0].toInt() * str[1].toInt()
    }.sum()
}

fun enabledMulSearch(input: String): Int {
    var index = 0
    var total = 0
    while(true) {
        val disMatch = "don't\\(\\)".toRegex().find(input, startIndex = index)
        if(disMatch == null) {
            total += uncorruptedMulSearch(input.substring(index))
            break
        }
        total += uncorruptedMulSearch(input.substring(index, disMatch.range.first))
        val enMatch = "do\\(\\)".toRegex().find(input, startIndex = disMatch.range.last + 1) ?: break
        index = enMatch.range.last + 1
    }
    return total
}