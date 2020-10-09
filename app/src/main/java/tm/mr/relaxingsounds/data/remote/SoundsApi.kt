package tm.mr.relaxingsounds.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import tm.mr.relaxingsounds.data.model.BaseResponse
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Sound

interface SoundsApi {

    //https://us-central1-awesome0-86d59.cloudfunctions.net/sounds?lastId=soundId101&categoryId=categoryId0&limit=5
    @GET("sounds")
    suspend fun sounds(@Query("lastId") lastId: String? = null, @Query("categoryId") categoryId: String? = null, @Query("limit") limit: Int? = null): BaseResponse<List<Sound>>

    //https://us-central1-awesome0-86d59.cloudfunctions.net/categories
    @GET("categories")
    suspend fun categories(): BaseResponse<List<Category>>

}