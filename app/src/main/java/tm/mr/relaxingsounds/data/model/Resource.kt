package tm.mr.relaxingsounds.data.model

sealed class Resource<out T : Any> {
    data class success<out T : Any>(val data: T) : Resource<T>()
    data class error(val msg: String) : Resource<Nothing>()
    object loading : Resource<Nothing>()
}