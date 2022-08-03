package handlers

import core.Updating
import org.json.JSONObject

interface BotRecognizerEvent: Updating.Mapper<JSONObject> {
    object Dummy : BotRecognizerEvent {
        override fun map(updating: JSONObject) = updating
    }
}