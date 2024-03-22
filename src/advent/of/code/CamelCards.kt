package advent.of.code

import java.io.File

private fun isFiveOfAKind1(hand: String): Boolean {
    if (hand.toSet().size == 1) {
        return true
    }
    return false
}

private fun isFourOfAKind1(hand: String): Boolean {
    if (hand.toList().groupingBy { it }.eachCount().values.contains(4)) {
        return true
    }
    return false
}

private fun isFullHouse1(hand: String): Boolean {
    val cardMap = hand.toList().groupingBy { it }.eachCount()
    if (cardMap.size == 2 && cardMap.values.contains(3)) {
        return true
    }
    return false
}

private fun isThreeOfAKind1(hand: String): Boolean {
    val cardMap = hand.toList().groupingBy { it }.eachCount()
    if (cardMap.size == 3 && cardMap.values.contains(3)) {
        return true
    }
    return false
}

private fun isTwoPair1(hand: String): Boolean {
    val cardMap = hand.toList().groupingBy { it }.eachCount()
    if (cardMap.size == 3 && cardMap.values.contains(2)) {
        return true
    }
    return false
}

private fun isOnePair1(hand: String): Boolean {
    val cardMap = hand.toList().groupingBy { it }.eachCount()
    if (cardMap.size == 4 && cardMap.values.contains(2)) {
        return true
    }
    return false
}

private fun rankHand1(hand: String): Int {
    var rank = 0
    if (isFiveOfAKind1(hand)) {
        rank = 6
    } else if (isFourOfAKind1(hand)) {
        rank = 5
    } else if (isFullHouse1(hand)) {
        rank = 4
    } else if (isThreeOfAKind1(hand)) {
        rank = 3
    } else if (isTwoPair1(hand)) {
        rank = 2
    } else if (isOnePair1(hand)) {
        rank = 1
    }

    return rank
}

private fun isFiveOfAKind2(hand: String): Boolean {
    val cardSet = hand.toSet()
    if (cardSet.size == 1 || (cardSet.size == 2 && cardSet.contains('J'))) {
        return true
    }
    return false
}

private fun isFourOfAKind2(hand: String): Boolean {
    val cardMap = hand.toList().groupingBy { it }.eachCount()
    if (cardMap.values.contains(4)) {
        return true
    } else if (cardMap.values.contains(3) && cardMap['J'] == 1) {
        return true
    } else if (cardMap.size == 3 && cardMap['J'] in 2..3) {
        return true
    }
    return false
}

private fun isFullHouse2(hand: String): Boolean {
    val cardMap = hand.toList().groupingBy { it }.eachCount()
    if (cardMap.size == 2 && cardMap.values.contains(3)) {
        return true
    } else if (cardMap.size == 3 && cardMap['J'] == 1) {
        return true
    }
    return false
}

private fun isThreeOfAKind2(hand: String): Boolean {
    val cardMap = hand.toList().groupingBy { it }.eachCount()
    if (cardMap.size == 3 && cardMap.values.contains(3)) {
        return true
    } else if (cardMap.values.contains(2) && cardMap['J'] == 1) {
        return true
    } else if (cardMap['J'] == 2) {
        return true
    }
    return false
}

private fun isTwoPair2(hand: String): Boolean {
    val cardMap = hand.toList().groupingBy { it }.eachCount()
    if (cardMap.size == 3 && cardMap.values.contains(2)) {
        return true
    }
    return false
}

private fun isOnePair2(hand: String): Boolean {
    val cardMap = hand.toList().groupingBy { it }.eachCount()
    if (cardMap.size == 4) {
        return true
    } else if (cardMap.size == 5 && cardMap['J'] == 1) {
        return true
    }
    return false
}

private fun rankHand2(hand: String): Int {
    var rank = 0
    if (isFiveOfAKind2(hand)) {
        rank = 6
    } else if (isFourOfAKind2(hand)) {
        rank = 5
    } else if (isFullHouse2(hand)) {
        rank = 4
    } else if (isThreeOfAKind2(hand)) {
        rank = 3
    } else if (isTwoPair2(hand)) {
        rank = 2
    } else if (isOnePair2(hand)) {
        rank = 1
    }

    return rank
}

private fun compareHands1(hand1: String, hand2: String): Int {
    val rank1 = rankHand1(hand1)
    val rank2 = rankHand1(hand2)
    val strength = mapOf(
        'A' to 14,
        'K' to 13,
        'Q' to 12,
        'J' to 11,
        'T' to 10,
        '9' to 9,
        '8' to 8,
        '7' to 7,
        '6' to 6,
        '5' to 5,
        '4' to 4,
        '3' to 3,
        '2' to 2
    )
    if (rank1 != rank2) {
        return rank1 - rank2
    } else {
        for (i in 0..4) {
            if (strength[hand1[i]] != strength[hand2[i]]) {
                return strength[hand1[i]]!! - strength[hand2[i]]!!
            }
        }
        return 0
    }
}

private fun compareHands2(hand1: String, hand2: String): Int {
    val rank1 = rankHand2(hand1)
    val rank2 = rankHand2(hand2)
    val strength = mapOf(
        'A' to 14,
        'K' to 13,
        'Q' to 12,
        'J' to 1,
        'T' to 10,
        '9' to 9,
        '8' to 8,
        '7' to 7,
        '6' to 6,
        '5' to 5,
        '4' to 4,
        '3' to 3,
        '2' to 2
    )
    if (rank1 != rank2) {
        return rank1 - rank2
    } else {
        for (i in 0..4) {
            if (strength[hand1[i]] != strength[hand2[i]]) {
                return strength[hand1[i]]!! - strength[hand2[i]]!!
            }
        }
        return 0
    }
}


private fun processHands1(rawhands: List<String>): Int {
    val hands = rawhands.map { hand -> Pair(hand.split(' ')[0].trim(), hand.split(' ')[1].trim().toInt()) }.toList()
    val sortedHands = hands.sortedWith { h1, h2 -> compareHands1(h1.first, h2.first) }
    return sortedHands.map { pair -> pair.second }.reduceIndexed { index, acc, value -> acc + ((index + 1) * value) }
}

private fun processHands2(rawhands: List<String>): Int {
    val hands = rawhands.map { hand -> Pair(hand.split(' ')[0].trim(), hand.split(' ')[1].trim().toInt()) }.toList()
    val sortedHands = hands.sortedWith { h1, h2 -> compareHands2(h1.first, h2.first) }
    return sortedHands.map { pair -> pair.second }.reduceIndexed { index, acc, value -> acc + ((index + 1) * value) }

}

fun main() {
    val filename = "./data/day7.txt"
    // part 1
    try {
        println(processHands1(File(filename).readLines()))
    } catch (e: Exception) {
        print(e.message)
    }

    // part 1
    try {
        println(processHands2(File(filename).readLines()))
    } catch (e: Exception) {
        print(e.message)
    }

}