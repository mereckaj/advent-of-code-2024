package day1

import java.io.File
import kotlin.math.abs

val listsOfLocations = File("day1/input.txt")
    .readLines()
    .map { it.split(Regex("\\s{3}")).map(String::toInt) }

fun challengeOne() {
    val listOne = listsOfLocations.map { it[0] }.sorted()
    val listTwo = listsOfLocations.map { it[1] }.sorted()
    val totalDistance = listOne.zip(listTwo).sumOf { abs(it.first - it.second) }

    println("#1 answer is $totalDistance")
}

fun challengeTwo() {
    val listOne = listsOfLocations.map { it[0] }
    val countsInTwo = listsOfLocations.map { it[1] }.groupingBy { it }.eachCount()

    val similarityScore = listOne.sumOf { it * countsInTwo.getOrDefault(it, 0) }

    println("#2 answer is $similarityScore")
}

fun main() {
    challengeOne()
    challengeTwo()
}