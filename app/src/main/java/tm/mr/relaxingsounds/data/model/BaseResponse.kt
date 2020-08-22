package tm.mr.relaxingsounds.data.model

data class BaseResponse<T>(
    val data: T?,
    val error: String?
)