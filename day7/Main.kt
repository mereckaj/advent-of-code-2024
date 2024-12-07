package day7

import java.io.File

fun main() {
    val input = File("day7/input.txt")
        .readLines()
    challengeOne(input)
    challengeTwo(input)
}

fun challengeTwo(input: List<String>) {
    val result = input
        .map {
            val splits = it.split(":")
            splits[0].toLong() to splits[1].trim().split(" ").map(String::toLong)
        }
        .map { it.first to Pair(it.second, calculateTwo(it.second)) }
        .filter { it.second.second.contains(it.first) }
        .sumOf { it.first }

    println(result)
}

fun challengeOne(input: List<String>) {
    val result = input
        .map {
            val splits = it.split(":")
            splits[0].toLong() to splits[1].trim().split(" ").map(String::toLong)
        }
        .map { it.first to calculateOne(it.second) }
        .filter { it.second.contains(it.first) }
        .sumOf { it.first }

    println(result)
}

fun calculateOne(input: List<Long>): List<Long> {
    val (first, rest) = input.split()
    var soFar: List<Long> = mutableListOf(first)
    for (i in rest) {
        soFar = calculateOne(i, soFar)
    }

    return soFar
}
fun calculateTwo(input: List<Long>): List<Long> {
    val (first, rest) = input.split()
    var soFar: List<Long> = mutableListOf(first)
    for (i in rest) {
        soFar = calculateTwo(i, soFar)
    }

    return soFar
}

fun calculateOne(input: Long, soFar: List<Long>): List<Long> {
    return soFar.flatMap {
        listOf(
            input + it,
            input * it
        )
    }
}

fun calculateTwo(input: Long, soFar: List<Long>): List<Long> {
    return soFar.flatMap {
        listOf(
            input + it,
            input * it,
            "$it$input".toLong()
        )
    }
}

fun <T> List<T>.split() = Pair(first(), drop(1))