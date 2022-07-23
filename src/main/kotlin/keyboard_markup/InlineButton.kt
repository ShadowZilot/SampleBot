package keyboard_markup

import org.json.JSONObject

class InlineButton(
    text: String,
    private val mUrl: String = "",
    private val mCallbackData: String = ""
) : KeyboardButton(text) {

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mText,
        mUrl,
        mCallbackData
    )

    interface Mapper<T> {
        fun map(
            text: String,
            url: String,
            callbackData: String
        ): T
    }

    override fun toJSON() = JSONObject().apply {
        put("text", mText)
        put("url", mUrl)
        put("callback_data", mCallbackData)
    }
}