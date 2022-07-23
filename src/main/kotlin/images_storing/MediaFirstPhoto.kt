package images_storing

class MediaFirstPhoto : MediaItem.Mapper<String> {

    override fun map(media: List<Pair<String, String>>): String {
        return media.first().second
    }
}