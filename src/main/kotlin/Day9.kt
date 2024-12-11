private fun main() {
    val testInput = "2333133121414131402"
    //println(process(testInput))
    println("00992111777.44.333....5555.6666.....8888..".mapIndexed { index, c -> if(c == '.') 0 else c.digitToInt() * index }.sum())
    println(checksum(process(testInput)))
    println(checksum(process(getResourceAsText("day9.txt")!!)))
}

private fun process(input: String): Pair<List<Pair<Int, Int>>, List<Int>> {
    val blocks: MutableList<Pair<Int, Int>> = mutableListOf()
    val space: MutableList<Int> = mutableListOf()
    input.trim().forEachIndexed { index, c ->
        val free = index % 2
        val i = c.digitToInt()
        if(free == 0) {
            blocks.add(Pair(blocks.size, i))
        } else {
            space.add(i)
        }
    }
    return Pair(blocks, space)
}

private fun Pair<Int, Int>.pairList() = List(this.second) { this.first }

private fun checksum(input: Pair<List<Pair<Int, Int>>, List<Int>>): Long {
    val blocks = input.first.toMutableList()
    val space = input.second.toMutableList()
    val output: MutableList<Int> = blocks[0].pairList().toMutableList()
    blocks.removeAt(0)
    while(space.size > 0 && blocks.size > 0) {
        val (id, size) = blocks.last()
        val current = space.first()
        output.addAll(Pair(id, Math.min(current, size)).pairList())
        if(current > size) {
            blocks.removeLast()
            space[0] = current - size
        }
        else if(size > current) {
            space.removeFirst()
            blocks[blocks.size - 1] = Pair(id, size - current)
        } else {
            space.removeFirst()
            blocks.removeLast()
        }
        if(size >= current && blocks.size > 0) {
            output.addAll(blocks.removeFirst().pairList())
        }
    }
    if(blocks.size > 0) {
        for(p in blocks) {
            output.addAll(p.pairList())
        }
    }
    return output.mapIndexed { index, i -> (index * i).toLong() }.sum()
}

sealed class Block

data class FreeSpace(val size: Int) : Block()
data class FileSpace(val size: Int, val id: Int) : Block()

private fun notFragmentedChecksum(input: String): Long {
    val blocks: List<Block> = input.trim().mapIndexed { index, char ->
        val file = index % 2
        val size = char.digitToInt()
        if(file == 0) FileSpace(size, index / 2) else FreeSpace(size)
    }
    for(i in blocks.indices.reversed()) {
        
    }
    return 0L
}