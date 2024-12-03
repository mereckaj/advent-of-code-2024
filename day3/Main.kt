package day3

import java.io.File

val input = File("day3/input.txt")
    .readLines()
    .joinToString("")
fun first() {
    val regex = "mul\\((?<a>\\d{1,3}),(?<b>\\d{1,3})\\)".toRegex()
    val matches = regex.findAll(input)
        .map { it.groupValues.get(1).toInt() * it.groupValues.get(2).toInt() }
        .sum()
    println(matches)
}
fun second() {
    val regex = "mul\\((?<a>\\d{1,3}),(?<b>\\d{1,3})\\)|do\\(\\)|don\'t\\(\\)".toRegex()
    var enabled = true
    var sum = 0
    regex.findAll(input).forEach {
        if(it.value == "do()"){
            enabled = true
        } else if (it.value == "don\'t()"){
            enabled = false
        } else {
            if (enabled){
                sum += it.groupValues[1].toInt() * it.groupValues[2].toInt()
            }
        }
    }
    println(sum)
}

fun main () {
    first()
    second()
}