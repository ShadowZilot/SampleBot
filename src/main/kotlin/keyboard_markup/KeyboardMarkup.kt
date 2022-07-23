package keyboard_markup

import org.json.JSONObject

interface KeyboardMarkup {

    fun filed(): JSONObject

    class Dummy : KeyboardMarkup {
        override fun filed(): JSONObject {
            return JSONObject()
        }
    }
}