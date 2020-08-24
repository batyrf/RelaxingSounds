package tm.mr.relaxingsounds.data.extension

fun Boolean?.ignoreNull(default: Boolean? = null): Boolean = this ?: default ?: false

fun Int?.ignoreNull(default: Int? = null): Int = this ?: default ?: 0

fun String?.ignoreNull(default: String? = null): String = this ?: default ?: ""

fun Float?.ignoreNull(default: Float? = null): Float = this ?: default ?: 0F

fun <E> List<E>?.ignoreNull( default: List<E>? = null ): List<E> = this ?: default ?: listOf()