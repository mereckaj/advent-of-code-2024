package `Day 1`

import java.io.File
import kotlin.math.abs

val listsOfLocations = File("./Day 1/input.txt")
    .readLines()
    .map { it.split(Regex("\\s{3}")).map(String::toInt) }

fun challengeOne() {

    val listOne = listsOfLocations.map { it.get(0) }.toList().sorted()
    val listTwo = listsOfLocations.map { it.get(1) }.toList().sorted()
    val totalDistance = listOne.zip(listTwo).map { abs(it.first - it.second) }.sum()

    println("#1 answer is $totalDistance")
}

fun challengeTwo() {

    val listOne = listsOfLocations.map { it.get(0) }.toList()
    val countsInTwo = listsOfLocations.map { it.get(1) }.toList().groupingBy { it }.eachCount()

    val similarityScore = listOne.map { it * countsInTwo.getOrDefault(it,0) }.sum()

    println("#2 answer is $similarityScore")
}

fun main() {
    challengeOne()
    challengeTwo()
}