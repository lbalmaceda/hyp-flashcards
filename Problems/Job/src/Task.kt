import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val can = scanner.nextInt() in 18..59
    println(if (can) "YES" else "NO")
}