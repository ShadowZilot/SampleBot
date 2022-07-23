package keyboard_markup

import org.json.JSONObject

abstract class KeyboardButton(
    protected val mText: String
) {

    abstract fun toJSON(): JSONObject
}