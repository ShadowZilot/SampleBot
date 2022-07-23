package keyboard_markup

import org.json.JSONArray
import org.json.JSONObject

class InlineKeyboardMarkup(
    private val mButtons: List<List<KeyboardButton>>
) : KeyboardMarkup {

    override fun filed(): JSONObject {
        val result = JSONObject()
        result.put("inline_keyboard", JSONArray().apply {
            for (i in mButtons.indices) {
                this.put(JSONArray().apply {
                    for (j in mButtons[i].indices) {
                        put(mButtons[i][j].toJSON())
                    }
                })
            }
        })
        return result
    }
}