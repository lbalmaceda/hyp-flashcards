package flashcards

import java.io.File
import java.util.*
import kotlin.system.exitProcess


var outputFile: String? = null
val scanner = Scanner(System.`in`)
val mapOfCards: MutableMap<String, Pair<String, Int>> = mutableMapOf()
val ioLog: MutableList<String> = mutableListOf()

fun main(args: Array<String>) {
    parseArguments(args)

    while (scanner.hasNextLine()) {
        printAndLog("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
        when (readAndLog()) {
            "add" -> addCard()
            "remove" -> removeCard()
            "ask" -> beginAsking()
            "import" -> importCards()
            "export" -> exportCards()
            "log" -> saveLog()
            "hardest card" -> findHardestCard()
            "reset stats" -> resetStats()
            "exit" -> finish()
        }
    }
}

fun parseArguments(args: Array<String>) {
    for (i in args.indices) {
        if (args[i] == "-import") {
            importCards(filename = args[i + 1])
        }
        if (args[i] == "-export") {
            outputFile = args[i + 1]
        }
    }
}

fun resetStats() {
    for (card in mapOfCards) {
        mapOfCards[card.key] = Pair(card.value.first, 0)
    }
    printAndLog("Card statistics has been reset.")
}

fun findHardestCard() {
    val maxErrors = mapOfCards.filterValues { it.second > 0 }.maxBy { it.value.second }?.value?.second
    if (maxErrors == null) {
        printAndLog("There are no cards with errors.")
        return
    }
    val hardestCards = mapOfCards.filter { it.value.second == maxErrors }
    if (hardestCards.size == 1) {
        val term = hardestCards.keys.first()
        printAndLog("""The hardest card is "${term}". You have ${maxErrors} errors answering it.""")
    } else {
        val termList = hardestCards.map { it.key }.toSet()
        val terms = termList.joinToString(prefix = "\"", postfix = "\"")
        printAndLog("""The hardest cards are ${terms}. You have ${maxErrors} errors answering them.""")
    }
}

fun saveLog() {
    printAndLog("File name:")
    val name = readAndLog()
    val file = File(name)
    if (!file.exists()) {
        file.createNewFile()
    }
    file.writeText(ioLog.joinToString(separator = "\n"))
    printAndLog("The log has been saved.")
}

fun exportCards(filename: String = askFilename()) {
    val file = File(filename)
    if (!file.exists()) {
        file.createNewFile()
    }

    val writer = file.writer()
    for (card in mapOfCards) {
        writer.write(card.key)
        writer.appendln()
        writer.write(card.value.first)
        writer.appendln()
        writer.write(card.value.second.toString())
        writer.appendln()
    }
    writer.close()
    printAndLog("${mapOfCards.size} cards have been saved. ${deckString()}")
}

fun importCards(filename: String = askFilename()) {
    val file = File(filename)
    if (!file.exists()) {
        printAndLog("File not found.")
        return
    }

    val lines = file.readLines()
    var i = 0
    while (i < lines.size) {
        val term = lines[i++]
        val definition = lines[i++]
        val errors = lines[i++].toIntOrNull() ?: 0
        mapOfCards[term] = Pair(definition, errors)
    }
    printAndLog("${lines.size / 3} cards have been loaded. ${deckString()}")
}

fun askFilename(): String {
    printAndLog("File name:")
    return readAndLog()
}

fun deckString(): String {
    val sb = StringBuilder()
    for (card in mapOfCards) {
        sb.append("""("${card.key}":"${card.value}") """)
    }
    return sb.toString()
}

fun finish() {
    printAndLog("Bye bye!")
    outputFile?.let { exportCards(it) }
    exitProcess(0)
}

fun beginAsking() {
    printAndLog("How many times to ask?")
    val times = readAndLog().toInt()

    val random = Random()
    repeat(times) {
        val nextIndex = random.nextInt(mapOfCards.size)
        val term = mapOfCards.keys.elementAt(nextIndex)
        val cardData = mapOfCards[term]!!
        val definition = cardData.first
        printAndLog("""Print the definition of "$term"""")
        val input = readAndLog()
        when {
            input == definition -> {
                printAndLog("Correct answer.")
            }
            mapOfCards.filter { it.value.first == input }.any() -> {
                val otherTerm = mapOfCards.filter { it.value.first == input }.keys.first()
                printAndLog("""Wrong answer. The correct one is "$definition", you've just written the definition of "$otherTerm".""")
                mapOfCards[term] = Pair(cardData.first, cardData.second + 1)
            }
            else -> {
                printAndLog("""Wrong answer. The correct one is "$definition".""")
                mapOfCards[term] = Pair(cardData.first, cardData.second + 1)
            }
        }
    }
}

fun removeCard() {
    printAndLog("The card:")
    val term = readAndLog()
    if (!mapOfCards.containsKey(term)) {
        printAndLog("""Can't remove "$term": there is no such card.""")
        return
    }
    mapOfCards.remove(term)
    printAndLog("The card has been removed. ${deckString()}")
}

fun addCard() {
    printAndLog("The card:")
    val term = readAndLog()
    if (mapOfCards.mapKeys { it.key.toLowerCase() }.contains(term.toLowerCase())) {
        printAndLog("""The card "$term" already exists.""")
        return
    }
    printAndLog("The definition of the card:")
    val definition = readAndLog()
    if (mapOfCards.mapValues { it.value.first.toLowerCase() }.containsValue(definition.toLowerCase())) {
        printAndLog("""The definition "$definition" already exists.""")
        return
    }

    mapOfCards[term] = Pair(definition, 0) //FIXME: could change existing errors
    printAndLog("""The pair ($term:$definition) has been added. ${deckString()}""")
}

fun printAndLog(msg: String) {
    println(msg)
    ioLog.add(msg)
}

fun readAndLog(): String {
    val msg = scanner.nextLine()
    ioLog.add(msg)
    return msg
}
