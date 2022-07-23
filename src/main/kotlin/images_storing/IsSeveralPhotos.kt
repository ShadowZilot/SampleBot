package images_storing

import core.Updating
import updating.UpdatingIsFromPhotos

interface IsSeveralPhotos {

    fun checked(updates: List<Updating>): Boolean

    class Base : IsSeveralPhotos {

        override fun checked(updates: List<Updating>): Boolean {
            return if (updates.size >= 2) {
                var result = false
                for (i in 1 until updates.size) {
                    if (updates[i-1].map(UpdatingIsFromPhotos(updates[i]))) {
                        result = true
                    } else {
                        result = false
                        break
                    }
                }
                return result
            } else {
                false
            }
        }
    }
}