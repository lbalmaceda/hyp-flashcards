import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val parts = scanner.nextInt()
    val out = intArrayOf(0, 0, 0)
    repeat(parts){
        val status = scanner.nextInt()
        out[status+1]++
    }
    println("${out[1]} ${out[2]} ${out[0]}")
}