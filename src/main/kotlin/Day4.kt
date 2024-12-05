fun main() {
    val input = getResourceAsText("day4.txt")!!.trim().split("\n")
    println(xmasSearch(listOf("MMMSXXMASM", "MSAMXMSMSA", "AMXSXMAAMM", "MSAMASMSMX", "XMASAMXAMM", "XXAMMXXAMA", "SMSMSASXSS", "SAXAMASAAA", "MAMMMXMMMM", "MXMXAXMASX")))
    println(xmasSearch(input))
    println(xmasShapeSearch(listOf("MMMSXXMASM", "MSAMXMSMSA", "AMXSXMAAMM", "MSAMASMSMX", "XMASAMXAMM", "XXAMMXXAMA", "SMSMSASXSS", "SAXAMASAAA", "MAMMMXMMMM", "MXMXAXMASX")))
    println(xmasShapeSearch(input))
}

fun xmasSearch(lines: List<String>): Int {
    var count = 0
    for(i in lines.indices) {
        val line = lines[i]
        var index = 0
        while(true) {
            val start = line.indexOf("X", index)
            if(start == -1) {
                break
            }
            if(start > 2 && line[start - 1] == 'M' && line[start - 2] == 'A' && line[start - 3] == 'S')
                count++
            if(start < line.length - 3 && line[start + 1] == 'M' && line[start + 2] == 'A' && line[start + 3] == 'S')
                count++
            if(i > 2 && lines[i - 1][start] == 'M' && lines[i - 2][start] == 'A' && lines[i - 3][start] == 'S')
                count ++
            if(i < lines.size - 3 && lines[i + 1][start] == 'M' && lines[i + 2][start] == 'A' && lines[i + 3][start] == 'S')
                count ++
            if(i > 2 && start > 2 && lines[i - 1][start - 1] == 'M' && lines[i - 2][start - 2] == 'A' && lines[i - 3][start - 3] == 'S')
                count ++
            if(i < lines.size - 3 && start < line.length - 3 && lines[i + 1][start + 1] == 'M' && lines[i + 2][start + 2] == 'A' && lines[i + 3][start + 3] == 'S')
                count ++
            if(i < lines.size - 3 && start > 2 && lines[i + 1][start - 1] == 'M' && lines[i + 2][start - 2] == 'A' && lines[i + 3][start - 3] == 'S')
                count ++
            if(i > 2 && start < line.length - 3 && lines[i - 1][start + 1] == 'M' && lines[i - 2][start + 2] == 'A' && lines[i - 3][start + 3] == 'S')
                count ++
            index = start + 1
        }
    }
    return count
}

fun xmasShapeSearch(lines: List<String>): Int {
    var count = 0
    for(i in lines.indices) {
        val line = lines[i]
        var index = 0
        while(true) {
            val start = line.indexOf("A", index)
            if(start == -1) {
                break
            }
            if(i > 0 && i < lines.size - 1 && start > 0 && start < line.length - 1) {
                val a = lines[i - 1][start - 1]
                val b = lines[i - 1][start + 1]
                val c = lines[i + 1][start - 1]
                val d = lines[i + 1][start + 1]
                if(((a == 'M' && d == 'S') || (a == 'S' && d == 'M')) && ((b == 'M' && c == 'S') || (b == 'S' && c == 'M')))
                    count++
            }
            index = start + 1
        }
    }
    return count
}