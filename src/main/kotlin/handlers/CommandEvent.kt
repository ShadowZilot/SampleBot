package handlers

import org.json.JSONException
import org.json.JSONObject

// Поменять на имя твоего бота
private const val sBotName = ""

class CommandEvent(
    private val mCommand: String
) : BotRecognizerEvent {

    override fun map(updating: JSONObject): JSONObject {
        try {
            val entity = updating.getJSONObject("message")
                .getJSONArray("entities")
                .getJSONObject(0)
            val range = IntRange(
                entity.getInt("offset"),
                entity.getInt("length") + entity.getInt("offset") - 1
            )
            updating.getJSONObject("message").apply {
                return if (getString("text").slice(range) == mCommand) {
                    updating
                } else if (getString("text").slice(range) == mCommand + sBotName) {
                    updating
                } else {
                    throw UnhandledEvent()
                }
            }
        } catch (e: JSONException) {
            throw UnhandledEvent()
        }
    }
}