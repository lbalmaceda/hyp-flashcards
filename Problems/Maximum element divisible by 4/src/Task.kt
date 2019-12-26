import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    // put your code here
    val count = scanner.nextInt()
    var max = 0
    repeat(count) {
        val curr = scanner.nextInt()
        if (curr % 4 == 0 && curr > max) {
            max = curr
        }
    }
    println(max)
}