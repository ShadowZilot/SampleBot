package users_test

import all_users_handling.storage.UserId
import all_users_handling.storage.UserNotFoundException
import core.Updating
import helpers.storage.jdbc_wrapping.DatabaseHelper
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import users.AllUsersStorage
import kotlin.test.Test
import kotlin.test.assertEquals

class UsersDatabaseTest {
    private val mUserStorage = AllUsersStorage.Base(
        "users",
        DatabaseHelper.Base.Instance.provideInstance()
    ).apply {
        DatabaseHelper.Base.Instance.provideInstance().createTable(
            this.tableSchema()
        )
    }

    @Test
    fun insertEntity() {
        runBlocking {
            val actualUserId = mUserStorage.rewrittenUser(
                Updating(
                    JSONObject(
                        "{\n" +
                                "        \"update_id\": 57716180,\n" +
                                "        \"message\": {\n" +
                                "            \"message_id\": 898,\n" +
                                "            \"from\": {\n" +
                                "                \"id\": 1129163878,\n" +
                                "                \"is_bot\": false,\n" +
                                "                \"first_name\": \"\\u041c\\u0430\\u0440\\u043a\",\n" +
                                "                \"last_name\": \"\\u0422\\u0443\\u043b\\u043b\\u0438\\u0439\",\n" +
                                "                \"username\": \"ShadowZilot\",\n" +
                                "                \"language_code\": \"en\",\n" +
                                "                \"is_premium\": true\n" +
                                "            },\n" +
                                "            \"chat\": {\n" +
                                "                \"id\": 1129163878,\n" +
                                "                \"first_name\": \"\\u041c\\u0430\\u0440\\u043a\",\n" +
                                "                \"last_name\": \"\\u0422\\u0443\\u043b\\u043b\\u0438\\u0439\",\n" +
                                "                \"username\": \"ShadowZilot\",\n" +
                                "                \"type\": \"private\"\n" +
                                "            },\n" +
                                "            \"date\": 1663607953,\n" +
                                "            \"text\": \"/start\",\n" +
                                "            \"entities\": [{\n" +
                                "                \"offset\": 0,\n" +
                                "                \"length\": 6,\n" +
                                "                \"type\": \"bot_command\"\n" +
                                "            }]\n" +
                                "        }\n" +
                                "    }"
                    )
                )
            ).map(UserId())
            assertEquals(
                1129163878,
                actualUserId
            )
        }
    }

    @Test
    fun userByIdTest() {
        runBlocking {
            mUserStorage.rewrittenUser(
                Updating(
                    JSONObject(
                        "{\n" +
                                "        \"update_id\": 57716180,\n" +
                                "        \"message\": {\n" +
                                "            \"message_id\": 898,\n" +
                                "            \"from\": {\n" +
                                "                \"id\": 1129163878,\n" +
                                "                \"is_bot\": false,\n" +
                                "                \"first_name\": \"\\u041c\\u0430\\u0440\\u043a\",\n" +
                                "                \"last_name\": \"\\u0422\\u0443\\u043b\\u043b\\u0438\\u0439\",\n" +
                                "                \"username\": \"ShadowZilot\",\n" +
                                "                \"language_code\": \"en\",\n" +
                                "                \"is_premium\": true\n" +
                                "            },\n" +
                                "            \"chat\": {\n" +
                                "                \"id\": 1129163878,\n" +
                                "                \"first_name\": \"\\u041c\\u0430\\u0440\\u043a\",\n" +
                                "                \"last_name\": \"\\u0422\\u0443\\u043b\\u043b\\u0438\\u0439\",\n" +
                                "                \"username\": \"ShadowZilot\",\n" +
                                "                \"type\": \"private\"\n" +
                                "            },\n" +
                                "            \"date\": 1663607953,\n" +
                                "            \"text\": \"/start\",\n" +
                                "            \"entities\": [{\n" +
                                "                \"offset\": 0,\n" +
                                "                \"length\": 6,\n" +
                                "                \"type\": \"bot_command\"\n" +
                                "            }]\n" +
                                "        }\n" +
                                "    }"
                    )
                )
            )
            val actualUser = mUserStorage.userById(1129163878)
            assertEquals(
                1129163878,
                actualUser.map(UserId())
            )
        }
    }

    @Test
    fun noUserTest() {
        runBlocking {
            try {
                mUserStorage.userById(3)
                assert(false)
            } catch (e: UserNotFoundException) {
                assert(true)
            }
        }
    }
}
