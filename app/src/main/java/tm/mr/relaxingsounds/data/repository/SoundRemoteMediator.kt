package tm.mr.relaxingsounds.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import tm.mr.relaxingsounds.data.local.SoundDatabase
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.data.remote.SoundsApi
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SoundRemoteMediator @Inject constructor(
    private val db: SoundDatabase,
    private val api: SoundsApi
) : RxRemoteMediator<Int, Sound>() {

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
                    PREPEND -> Single.just(MediatorResult.Success(endOfPaginationReached = true))
                    REFRESH, APPEND -> api.sounds(lastId, categoryId, limit)
                            .map { it.data ?: listOf() }
                            .doOnSuccess {
                                db.soundDao().insertSounds(it)
                                    .subscribe({}, {})
                                    .addTo(CompositeDisposable())
                            }
                            .map { it.isEmpty() }
                            .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it) }
                            .onErrorReturn { MediatorResult.Error(it) }
                }
            }
            .onErrorReturn { MediatorResult.Error(it) }
    }



}