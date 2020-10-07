package tm.mr.relaxingsounds.data.repository

import androidx.paging.PagingData
import io.reactivex.Completable
import io.reactivex.Observable
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Sound

interface Repository {
    fun getSounds(
        categoryId: String? = null
    ): Observable<PagingData<Sound>>

    fun getCategories(): Observable<List<Category>>
    fun updateSound(sound: Sound): Completable
    fun getLikedSounds(): Observable<List<Sound>>
}
