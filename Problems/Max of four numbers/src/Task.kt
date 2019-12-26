import java.util.*
import kotlin.math.max
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    // write your code here
    var prev = 0
    while (scanner.hasNextInt()) {
        val curr = scanner.nextInt()
        prev = max(prev, curr)
    }
    println(prev)
}