package advent.of.code

import java.io.File

private fun calculateHash(step: String): Int {
    var current_value = 0
    for (c in step.toCharArray()) {
        current_value += c.code
        current_value *= 17
        current_value %= 256
    }
    return current_value
}

private fun calculateFocusingPower(steps: List<String>): Int {
    val boxes = mutableMapOf<Int, List<Pair<String, Int>>>()
    for (step in steps) {
        if (step.contains("=")) {
            val label = step.split("=")[0]
            val length = step.split("=")[1].toInt()
            val hash = calculateHash(label)
            if (!boxes.contains(hash)) {
                val box = mutableListOf<Pair<String, Int>>()
                boxes[hash] = box
            }
            val box = boxes[hash]
            if (box != null) {
                val new_box = mutableListOf<Pair<String, Int>>()
                var flag = false
                for (lens in box) {
                    if (lens.first == label) {
                        new_box.addLast(Pair(label, length))
                        flag = true
                    } else {
                        new_box.add(lens)
                    }
                }
                if (!flag) new_box.addLast(Pair(label, length))
                boxes[hash] = new_box
            }

        } else if (step.contains('-')) {
            val label = step.split("-")[0]
            val hash = calculateHash(label)
            val box = boxes[hash]
            val new_box = mutableListOf<Pair<String, Int>>()
            if (box != null) {
                for (lens in box) {
                    if (lens.first != label)
                        new_box.addLast(lens)
                }
                boxes[hash]=new_box
            }
        }
    }
    var power = 0
    for(n in boxes.keys){
        val box = boxes[n]
        if (box != null) {
            for(i in box.indices){
                power+=(n+1)*(i+1)*box[i].second
            }
        }
    }
    return power
}

fun main() {
    val filename = "./data/day15.txt"
    // part 1
    try {
        println(File(filename).readLines().first().split(",").map { step -> calculateHash(step) }.sum())
    } catch (e: Exception) {
        print(e.message)
    }

    // part 1
    try {
        println(calculateFocusingPower(File(filename).readLines().first().split(",")))
    } catch (e: Exception) {
        print(e.message)
    }
}