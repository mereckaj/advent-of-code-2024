package day2

import java.io.File
import kotlin.math.abs

val listsOfLevels = File("day2/input.txt")
    .readLines()
    .map { it.split(Regex("\\s")).map(String::toInt) }

fun challengeOne(input: List<List<Int>>) {
    val safeReportCount = input
        .asSequence()
        .map { isSafe(it) }
        .filter { it }
        .count()

    println("#1 answer is $safeReportCount")
}

fun challengeTwo(input: List<List<Int>>) {
    val safeReportCountWithProblemDampener = input
        .map(::createAllPossibleDampenedLists)
        .map { it.map(::isSafe).any { it } }
        .count { it }

    println("#2 answer is $safeReportCountWithProblemDampener")
}

fun createAllPossibleDampenedLists(input: List<Int>): List<List<Int>> {
    val lists = ArrayList<List<Int>>()
    lists.add(input)

    for (i in input.indices) lists.add(input.toMutableList().filterIndexed { index, _ -> if(index == i) false else true })

    return lists
}

fun isSafe(input: List<Int>): Boolean {
    return allIncreasingOrDecreasing(input) && hasSafeLevels(input.windowed(2) { m -> abs(m[0] - m[1]) })
}

fun allIncreasingOrDecreasing(input: List<Int>): Boolean {
    return input.sorted() == input || input.sorted().reversed() == input
}

fun hasSafeLevels(input: List<Int>): Boolean {
    val sorted = input.sorted()
    return sorted.min() > 0 && sorted.max() <= 3
}

fun main() {
    challengeOne(listsOfLevels)
    challengeTwo(listsOfLevels)
}
