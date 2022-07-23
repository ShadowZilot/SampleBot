package keyboard_markup

import org.json.JSONArray
import org.json.JSONObject

class ReplyKeyboardMarkup(
    private val mButton: List<List<KeyboardButton>>
) : KeyboardMarkup {

    override fun filed(): JSONObject {
        val result = JSONObject()
        result.put("keyboard", JSONArray().apply {
            for (i in mButton.indices) {
                this.put(JSONArray().apply {
                    for (j in mButton[i].indices) {
                        put(mButton[i][j].toJSON())
                    }
                })
            }
        })
        result.put("resize_keyboard", true)
        return result
    }
}