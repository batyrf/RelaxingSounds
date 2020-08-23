package tm.mr.relaxingsounds.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import io.reactivex.Observable
import tm.mr.relaxingsounds.data.local.SoundDatabase
import tm.mr.relaxingsounds.data.model.Sound
import javax.inject.Inject

class SoundRepository @Inject constructor() {

    @Inject
    lateinit var database: SoundDatabase

    @Inject
    lateinit var remoteMediator: SoundRemoteMediator

    fun getSounds(
        lastId: String? = null,
        categoryId: String? = null,
        limit: Int? = null
    ): Observable<PagingData<Sound>> {
        remoteMediator.lastId = lastId
        remoteMediator.categoryId = categoryId
        remoteMediator.limit = limit
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                categoryId?.let {
                    database.soundDao().listSoundsByCategory(it)
                } ?: database.soundDao().listSounds()
            }
        ).observable
    }

}