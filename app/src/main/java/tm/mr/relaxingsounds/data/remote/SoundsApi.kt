package tm.mr.relaxingsounds.data.remote

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import tm.mr.relaxingsounds.data.model.BaseResponse
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Sound

interface SoundsApi {

    //https://us-central1-awesome0-86d59.cloudfunctions.net/sounds?lastId=soundId101&categoryId=categoryId0&limit=5
    @GET("sounds")
    fun sounds(@Query("lastId") lastId: String? = null, @Query("categoryId") categoryId: String? = null, @Query("limit") limit: Int? = null): Single<BaseResponse<List<Sound>>>

    //https://us-central1-awesome0-86d59.cloudfunctions.net/categories
    @GET("categories")
    fun categories(): Observable<BaseResponse<List<Category>>>

}