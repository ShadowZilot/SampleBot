package core.interceptor

import updating.ChangePhotosInUpdating
import images_storing.IsSeveralPhotos
import updating.UpdatingMedia
import core.Updating
import org.json.JSONArray

class PhotosInterceptor(
    private val mInterceptor: UpdatesInterceptor
) : UpdatesInterceptor {
    private val mValidator = IsSeveralPhotos.Base()

    override fun updates(inputUpdates: List<Updating>): List<Updating> {
        val result = mutableListOf<Updating>()
        return if (mValidator.checked(inputUpdates)) {
            val photos = JSONArray()
            for (i in inputUpdates.indices) {
                photos.put(inputUpdates[i].map(UpdatingMedia()))
            }
            val firstItem = inputUpdates.first()
            result.add(
                firstItem.map(ChangePhotosInUpdating(photos))
            )
            mInterceptor.updates(result)
        } else {
            mInterceptor.updates(inputUpdates)
        }
    }
}