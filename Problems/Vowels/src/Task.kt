fun main() {
    val vowels = mapOf('a' to 1, 'e' to 5, 'i' to 9, 'o' to 15, 'u' to 21, 'y' to 25)
    val letter = readLine()!![0]
    val index = vowels.getOrDefault(letter, 0)
    println(index)
}