import java.util.*
import kotlin.math.atan2

fun main() {
    val scanner = Scanner(System.`in`)
    // put your code here
    val vec1 = scanner.nextLine().split(" ").map { it.toDouble() }
    val vec2 = scanner.nextLine().split(" ").map { it.toDouble() }
    val deg = atan2(vec1[1], vec1[0]) - atan2(vec2[1], vec2[0])
//    val deg = atan2(vec1[0] * vec2[1] - vec1[1] * vec2[0], vec1[0] * vec2[0] + vec1[1] * vec2[1])
    println(Math.toDegrees(kotlin.math.abs(deg)))
}