package images_storing

import org.json.JSONArray

data class SavingImageResult(
    private val mImages: List<ByteArray>,
    private val mFileIds: JSONArray
) {
    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mImages,
        mFileIds
    )

    interface Mapper<T> {
        fun map(
            images: List<ByteArray>,
            fileIds: JSONArray
        ) : T
    }
}