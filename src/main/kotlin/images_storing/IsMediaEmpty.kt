package images_storing

class IsMediaEmpty : MediaItem.Mapper<Boolean> {
    override fun map(media: List<Pair<String, String>>) = media.isEmpty()
}