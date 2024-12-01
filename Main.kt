import java.io.File

fun main() {
    val lists = File("./input.txt")
        .readLines()
        .map { it.split("   ").map(String::toInt) }
    val listOne = lists.map { it.get(0) }.toList().sorted()
    val listTwo = lists.map { it.get(1) }.toList().sorted()
    val totalDistance = listOne.zip(listTwo).map { Math.abs(it.first - it.second) }.sum()

    println(totalDistance)
}