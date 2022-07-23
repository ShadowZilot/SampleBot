package helpers

import keyboard_markup.KeyboardButton

fun List<KeyboardButton>.convertToVertical(): MutableList<List<KeyboardButton>> {
    val result = mutableListOf<List<KeyboardButton>>()
    forEach {
        result.add(listOf(it))
    }
    return result
}