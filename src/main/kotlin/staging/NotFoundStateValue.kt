package staging

class NotFoundStateValue(
    id: Long,
    key: String
) : java.lang.RuntimeException("State value with key = $key, user_id = $id not found!")