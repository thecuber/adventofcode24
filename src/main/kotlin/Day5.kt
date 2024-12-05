fun main() {
    val input = getResourceAsText("day5.txt")!!
    /*val input = "47|53\n" +
            "97|13\n" +
            "97|61\n" +
            "97|47\n" +
            "75|29\n" +
            "61|13\n" +
            "75|53\n" +
            "29|13\n" +
            "97|29\n" +
            "53|29\n" +
            "61|53\n" +
            "97|53\n" +
            "61|29\n" +
            "47|13\n" +
            "75|47\n" +
            "97|75\n" +
            "47|61\n" +
            "75|61\n" +
            "47|29\n" +
            "75|13\n" +
            "53|13\n" +
            "\n" +
            "75,47,61,53,29\n" +
            "97,61,53,29,13\n" +
            "75,29,13\n" +
            "75,97,47,61,53\n" +
            "61,13,29\n" +
            "97,13,75,29,47"*/
    val spl = input.split("\n\n")
    val priorities: MutableMap<Int, List<Int>> = mutableMapOf()
    spl[0].trim().split("\n").forEach {
        val s = it.split("|")
        val l = s[0].toInt()
        val r = s[1].toInt()
        val list = priorities.getOrDefault(l, listOf())
        priorities[l] = list.toMutableList().apply {
            add(r)
        }
    }
    val updates = spl[1].trim().split("\n").map { it.trim().split(",").map { a -> a.trim().toInt() } }
    println(pageSum(correctPageFilter(priorities, updates)))
    println(reorderedPageSum(priorities, updates))
}

fun pageSum(l: List<List<Int>>) = l.sumOf { it[(it.size - 1)/ 2] }

fun correctPageFilter(priorities: Map<Int, List<Int>>, updates: List<List<Int>>): List<List<Int>> {
    return updates.filter { update ->
        val list: MutableList<Int> = mutableListOf()
        var bool = true
        for (id in update) {
            val after = priorities.getOrDefault(id, listOf())
            if (list.any { it in after }) {
                bool = false
            }
            list.add(id)
        }
        bool
    }
}

fun reorderedPageSum(priorities: Map<Int, List<Int>>, updates: List<List<Int>>): Int {
    return pageSum(updates.filter { update ->
        val list: MutableList<Int> = mutableListOf()
        var bool = true
        for (id in update) {
            val after = priorities.getOrDefault(id, listOf())
            if (list.any { it in after }) {
                bool = false
            }
            list.add(id)
        }
        !bool
    }.map { it ->
        var bool = true
        var update = it
        while(bool) {
            val list: MutableList<Int> = mutableListOf()
            bool = false
            for (id in update) {
                val after = priorities.getOrDefault(id, listOf())
                val overlap = list.find { it in after }
                if (overlap != null) {
                    val index = list.indexOf(overlap)
                    update = update.toMutableList().apply {
                        this[list.size] = overlap
                        this[index] = id
                    }
                    bool = true
                    break
                }
                list.add(id)
            }
        }
        update
    })
}