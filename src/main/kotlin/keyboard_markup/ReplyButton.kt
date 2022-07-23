package keyboard_markup

import org.json.JSONObject

class ReplyButton(
    text: String,
) : KeyboardButton(text) {

    override fun toJSON() = JSONObject().apply {
        put("text", mText)
    }
}