import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val a = scanner.nextInt()
    val b = scanner.nextInt()
    var prod: Long = 1
    for (i in a until b) {
        prod *= i
    }
    println(prod)
}