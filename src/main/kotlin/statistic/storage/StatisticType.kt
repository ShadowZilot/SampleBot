package statistic.storage

sealed interface StatisticType {

    fun typeName(): String

    fun viewableName(): String

    object NewComing : StatisticType {

        override fun typeName() = "newComing"

        override fun viewableName() = "Новых пользователей"
    }

    object CommonAction : StatisticType {

        override fun typeName() = "commonAction"

        override fun viewableName() = "Взаимодействий с ботом"
    }
}