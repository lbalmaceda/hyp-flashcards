import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val input = scanner.nextInt()
    if(input in scanner.nextInt()..scanner.nextInt()){
        println("YES")
    } else {
        println("NO")
    }
}