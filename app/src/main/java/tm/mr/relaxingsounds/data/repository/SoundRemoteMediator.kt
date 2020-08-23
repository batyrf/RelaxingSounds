package tm.mr.relaxingsounds.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import tm.mr.relaxingsounds.data.local.SoundDatabase
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.data.remote.SoundsApi
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SoundRemoteMediator @Inject constructor() : RxRemoteMediator<Int, Sound>() {

    @Inject
    lateinit var db: SoundDatabase

    @Inject
    lateinit var api: SoundsApi

    var lastId: String? = null
    var categoryId: String? = null
    var limit: Int? = null

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, Sound>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .flatMap {
                when (it) {
                    REFRESH -> Single.never()
                    PREPEND -> Single.just(MediatorResult.Success(endOfPaginationReached = true))
                    APPEND -> api.sounds(lastId, categoryId, limit)
                        .map { it.data ?: listOf() }
                        .doOnSuccess {
                            it.map {
                                db.soundDao().insertSound(it)
                            }
                        }
                        .map { it.isEmpty() }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it) }
                        .onErrorReturn { MediatorResult.Error(it) }
                }
            }
            .onErrorReturn { MediatorResult.Error(it) }
    }



}