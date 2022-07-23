import users.UserToJson
import configuration.Config
import helpers.storage.JsonFile
import helpers.storage.StatesFileHelping
import helpers.storage.UserFileHelping
import images_storing.ImageSaving
import images_storing.MediaStorage
import stating.StateHandling
import stating.StateToJson
import users.AllUsersStorage
import java.io.File

object Storages {
    val stConfig = Config.Base(File("${sBasePath}config.json"))
    val stStateStorage = StateHandling.Base(
        StatesFileHelping(
            JsonFile.Base(File("${sBasePath}states.json")),
            StateToJson()
        )
    )
    val stUsersStorage = AllUsersStorage.Base(
        UserFileHelping(
            JsonFile.Base(File("${sBasePath}allUsers.json")),
            UserToJson()
        )
    )
    val stImageStorage = MediaStorage.Base(
        ImageSaving.Base(
            mClient,
            stConfig.configValueString("botKey"),
            "@fileGeneratorBot1"
        ), File("${sBasePath}kross_images")
    )
}