import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val a = scanner.nextInt()..scanner.nextInt()
    val b = scanner.nextInt()..scanner.nextInt()
    val num = scanner.nextInt()
    if (num in a && num in b){
        println("YES")
    } else {
        println("NO")
    }

    // write your code here
}