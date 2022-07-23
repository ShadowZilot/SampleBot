package images_storing

import logs.Logging
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

interface ImageSaving : MediaItem.Mapper<SavingImageResult> {

    fun saveByUrls(listUrls: List<String>): SavingImageResult

    fun saveImageByBytes(image: List<Pair<String, ByteArray>>): MediaItem

    class Base(
        private val mClient: OkHttpClient,
        private val mKey: String,
        private val mChatId: String
    ) : ImageSaving {

        override fun saveByUrls(listUrls: List<String>): SavingImageResult {
            val bytes = mutableListOf<ByteArray>()
            val array = JSONArray()
            for (i in listUrls.indices) {
                val response = mClient.newCall(
                    Request.Builder()
                        .url(listUrls[i])
                        .build()
                ).execute()
                bytes.add(response.body!!.bytes())
                val tgResponse = mClient.newCall(
                    Request.Builder()
                        .post(
                            MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("chat_id", mChatId)
                                .addFormDataPart(
                                    "photo", "image.jpeg",
                                    bytes.last().toRequestBody(
                                        "image/*jpeg".toMediaTypeOrNull(),
                                        0, bytes.last().size
                                    )
                                )
                                .build()
                        )
                        .url("https://api.telegram.org/bot${mKey}/sendPhoto")
                        .build()
                ).execute()
                val bodyString = tgResponse.body?.string()
                try {
                    array.put(
                        JSONObject(bodyString)
                            .getJSONObject("result")
                            .getJSONArray("photo")
                            .getJSONObject(0)
                            .getString("file_id")
                    )
                } catch (e: JSONException) {
                    Logging.ConsoleLog.log(bodyString!!)
                } finally {
                    tgResponse.body?.close()
                }
                response.body?.close()
            }
            return SavingImageResult(bytes, array)
        }

        override fun saveImageByBytes(image: List<Pair<String, ByteArray>>): MediaItem {
            val mediaList = mutableListOf<Pair<String, String>>()
            for (i in image.indices) {
                val type = image[i].first == "photo"
                val request = if (type) {
                    Request.Builder()
                        .post(MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("chat_id", mChatId)
                            .addFormDataPart("photo",
                                "photo", image[i].second.toRequestBody(
                                    "image/*jpeg".toMediaTypeOrNull()
                                ))
                            .build())
                        .url("https://api.telegram.org/bot${mKey}/sendPhoto")
                        .build()
                } else {
                    Request.Builder()
                        .post(MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("chat_id", mChatId)
                            .addFormDataPart("video",
                                "video", image[i].second.toRequestBody(
                                    "video/*mp4".toMediaTypeOrNull()
                                ))
                            .build())
                        .url("https://api.telegram.org/bot${mKey}/sendVideo")
                        .build()
                }
                val response = mClient.newCall(request).execute()
                val body = JSONObject(response.body!!.string())
                val id = if (type) {
                    val size = body.getJSONObject("result")
                        .getJSONArray("photo").length()
                    body.getJSONObject("result")
                        .getJSONArray("photo")
                        .getJSONObject(size - 1)
                        .getString("file_id")
                } else {
                    body.getJSONObject("result")
                        .getJSONObject("video")
                        .getString("file_id")
                }
                mediaList.add(
                    Pair(image[i].first, id)
                )
                response.body?.close()
                Thread.sleep(3000)
            }
            return MediaItem(mediaList)
        }

        override fun map(media: List<Pair<String, String>>): SavingImageResult {
            val array = MediaItem(media).map(MediaToJson())
            val bytes = mutableListOf<ByteArray>()
            for (i in media.indices) {
                val fileResponse = mClient.newCall(
                    Request.Builder()
                        .post(
                            FormBody.Builder()
                                .add("file_id", media[i].second)
                                .build()
                        )
                        .url("https://api.telegram.org/bot$mKey/getFile")
                        .build()
                ).execute()
                val bodyString = fileResponse.body!!.string()
                try {
                    val path = JSONObject(bodyString)
                        .getJSONObject("result")
                        .getString("file_path")
                    val imageResponse = mClient.newCall(
                        Request.Builder()
                            .url("https://api.telegram.org/file/bot$mKey/$path")
                            .build()
                    ).execute()
                    bytes.add(
                        imageResponse.body!!.bytes()
                    )
                    imageResponse.body?.close()
                } catch (e: JSONException) {
                    Logging.ConsoleLog.log(bodyString)
                }
                fileResponse.body?.close()
            }
            return SavingImageResult(bytes, array)
        }
    }
}