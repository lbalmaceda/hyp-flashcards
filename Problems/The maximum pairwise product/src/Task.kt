import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val ignored = scanner.nextLine();
    val split = scanner.nextLine().split(" ").map { Integer.parseInt(it) }
    val max = split.sorted().takeLast(2).reduce { acc, i -> acc * i }
    println(max)
}
