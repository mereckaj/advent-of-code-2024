package day4

import java.io.File

fun challengeOne(input: List<String>) {
    val strings = mutableSetOf<String>()

    strings.addAll(input)

    val columns = arrayOfNulls<String>(input.get(0).length)

    input.forEachIndexed { i, line ->
        line.forEachIndexed { j, c ->
            if (columns[j] == null) columns[j] = c.toString() else columns[j] = columns[j] + c.toString()
        }
    }

    strings.addAll(columns.filterNotNull())

    for (i in 0..<input.get(0).length) {
        var lineToRead = 0
        val diag = mutableListOf<Char>()
        for (j in i downTo 0) {
            diag += input.get(lineToRead).get(j)
            lineToRead += 1
        }
        strings.add(diag.joinToString(separator = ""))
    }

    for (lineToRead in 1..<input.size) {
        var dropRow = lineToRead
        val diag = mutableListOf<Char>()
        for (characterInRow in input.get(lineToRead).length - 1 downTo 0) {
            diag += input.get(dropRow).get(characterInRow)
            dropRow +=1
            if(dropRow >= input.size) {
                break
            }
        }
        strings.add(diag.joinToString(separator = ""))
    }

    for (lineToRead in input.size - 1 downTo 0) {
        var drop = 0
        val diag = mutableListOf<Char>()
        for (charToRead in input.get(lineToRead).length - 1 downTo 0){
            diag += input.get(lineToRead - drop).get(charToRead)
            drop += 1
            if(lineToRead - drop < 0){
                break
            }
        }
        strings.add(diag.joinToString(separator = ""))
    }

    for (lineToRead in input.size - 1 downTo 0){
        var drop = 0
        val diag = mutableListOf<Char>()
        for(charToRead in input.get(lineToRead).length - 1 downTo 0){
            diag += input.get(charToRead).get(lineToRead - drop)
            drop += 1
            if(lineToRead - drop < 0){
                break
            }
        }
        strings.add(diag.joinToString(separator = ""))
    }

    println(strings.filter { it.length >= 3 }.map {
        countOccurrences(it, "XMAS") + countOccurrences(it.reversed(), "XMAS")
    }.sum())
}

fun countOccurrences(str: String, searchStr: String): Int {
    var count = 0
    var startIndex = 0

    while (startIndex < str.length) {
        val index = str.indexOf(searchStr, startIndex)
        if (index >= 0) {
            count++
            startIndex = index + 1
        } else {
            break
        }
    }

    return count
}

fun challengeTwo(input: List<String>) {
    val x = input
        .map { it.toCharArray() }.toTypedArray()
}

fun main() {
    val puzzlePines = File("day4/input-small.txt").readLines()

//    challengeOne(puzzlePines)
    challengeTwo(puzzlePines)
}