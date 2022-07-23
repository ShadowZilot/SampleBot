package images_storing

import logs.Logging
import org.json.JSONArray
import java.io.File
import java.io.FileNotFoundException

interface MediaStorage {

    suspend fun saveImage(imageUrls: List<String>, articular: String)

    suspend fun saveImagesByMedia(imageIds: MediaItem, articular: String)

    fun fileIdsByArticular(articular: String): List<String>

    fun imageByArticular(articular: String): List<ByteArray>

    fun mediaByArticular(articular: String): MediaItem

    fun updateArticular(newArticular: String, oldArticular: String)

    class Base(
        private val mSaving: ImageSaving,
        private val mDirectory: File
    ) : MediaStorage {

        init {
            if (!mDirectory.exists()) {
                mDirectory.mkdir()
            }
        }

        override suspend fun saveImage(imageUrls: List<String>, articular: String) {
            val internalDirectory = File("${mDirectory.path}/$articular").apply {
                if (!exists()) mkdir()
            }
            mSaving.saveByUrls(imageUrls).map(object : SavingImageResult.Mapper<Unit> {
                override fun map(images: List<ByteArray>, fileIds: JSONArray) {
                    for (i in images.indices) {
                        File("${mDirectory.path}/${internalDirectory.name}/${i + 1}.png").apply {
                            createNewFile()
                            writeBytes(images[i])
                        }
                    }
                    File("${mDirectory.path}/${internalDirectory.name}/fileIds.json").apply {
                        createNewFile()
                        writeText(fileIds.toString(2))
                    }
                }
            })
        }

        override suspend fun saveImagesByMedia(imageIds: MediaItem, articular: String) {
            val internalDirectory = File("${mDirectory.path}/$articular").apply {
                if (!exists()) mkdir()
            }
            imageIds.map(mSaving).map(object : SavingImageResult.Mapper<Unit> {
                override fun map(images: List<ByteArray>, fileIds: JSONArray) {
                    for (i in images.indices) {
                        val extensions = if (fileIds.getJSONObject(i).getString("type") == "photo") {
                            "png"
                        } else {
                            "mp4"
                        }
                        File(
                            "${mDirectory.path}/${internalDirectory.name}/${i + 1}.$extensions"
                        ).apply {
                            createNewFile()
                            writeBytes(images[i])
                        }
                    }
                    File("${mDirectory.path}/${internalDirectory.name}/fileIds.json").apply {
                        createNewFile()
                        writeText(fileIds.toString(2))
                    }
                }
            })
        }

        override fun fileIdsByArticular(articular: String): List<String> {
            val media = JSONArray(
                File(
                    "${mDirectory.path}/$articular/fileIds.json"
                ).readText()
            )
            return MediaItem(media).map(object : MediaItem.Mapper<List<String>> {
                override fun map(media: List<Pair<String, String>>) = mutableListOf<String>().apply {
                    media.forEach {
                        add(it.second)
                    }
                }
            })
        }

        override fun imageByArticular(articular: String): List<ByteArray> {
            val images = File("${mDirectory.path}/$articular").listFiles() ?: return emptyList()
            return mutableListOf<ByteArray>().apply {
                for (i in images.sorted()) {
                    try {
                        if (i.name != "fileIds.json") {
                            add(i.readBytes())
                        }
                    } catch (e: FileNotFoundException) {
                        break
                    }
                }
            }
        }

        override fun mediaByArticular(articular: String): MediaItem {
            Logging.ConsoleLog.log(articular)
            val array = JSONArray(
                File("${mDirectory.path}/$articular/fileIds.json").readText()
            )
            return MediaItem(array)
        }

        override fun updateArticular(newArticular: String, oldArticular: String) {
            val images = File("${mDirectory.path}/$oldArticular").listFiles()?.toList()
                ?: throw FileNotFoundException()
            if (images.isNotEmpty()) {
                File("${mDirectory.path}/$oldArticular")
                    .renameTo(File("${mDirectory.path}/$newArticular"))
            }
        }
    }

    class Dummy : MediaStorage {
        override suspend fun saveImage(imageUrls: List<String>, articular: String) {}

        override suspend fun saveImagesByMedia(imageIds: MediaItem, articular: String) {}

        override fun fileIdsByArticular(articular: String): List<String> {
            return emptyList()
        }

        override fun imageByArticular(articular: String): List<ByteArray> {
            return emptyList()
        }

        override fun mediaByArticular(articular: String): MediaItem {
            return MediaItem(emptyList())
        }

        override fun updateArticular(newArticular: String, oldArticular: String) {}
    }
}