package images_storing

import org.json.JSONArray

data class MediaItem(
    private val mMediaIds: List<Pair<String, String>>
) {
    constructor(ids: JSONArray) : this(
        mutableListOf<Pair<String, String>>().apply {
            for (i in 0 until ids.length()) {
                ids.getJSONObject(i).let { item ->
                    add(
                        Pair(
                            item.getString("type"),
                            item.getString("id")
                        )
                    )
                }
            }
        }
    )

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.map(
            mMediaIds
        )
    }

    interface Mapper<T> {
        fun map(
            media: List<Pair<String, String>>
        ): T
    }
}