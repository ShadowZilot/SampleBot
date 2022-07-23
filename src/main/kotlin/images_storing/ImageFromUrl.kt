package images_storing

import okhttp3.OkHttpClient
import okhttp3.Request

interface ImageFromUrl {

    fun image(url: String): ByteArray

    class Base(
        private val mClient: OkHttpClient
    ) : ImageFromUrl {

        override fun image(url: String): ByteArray {
            val response = mClient.newCall(
                Request.Builder()
                    .url(url)
                    .build()
            ).execute()
            return response.body!!.bytes()
        }
    }
}