package tm.mr.relaxingsounds.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import tm.mr.relaxingsounds.data.local.SoundDatabase
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.data.remote.SoundsApi
import javax.inject.Inject

class SoundRepository @Inject constructor(
    private val api: SoundsApi,
    private val database: SoundDatabase,
    private val remoteMediator: SoundRemoteMediator
): Repository {

    override fun getSounds(
        categoryId: String?
    ): Observable<PagingData<Sound>> {
        remoteMediator.categoryId = categoryId
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                categoryId?.let {
                    database.soundDao().listSoundsByCategory(it)
                } ?: database.soundDao().listSounds()
            }
        ).observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCategories(): Observable<List<Category>> {
        return api.categories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.data ?: listOf()
            }
            .doOnNext {
                database.soundDao().insertCategories(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, {})
                    .addTo(CompositeDisposable())
            }
            .onErrorResumeNext(database.soundDao().listCategories())
    }

    override fun updateSound(sound: Sound): Completable {
        return database.soundDao().updateSound(sound)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getLikedSounds(): Observable<List<Sound>> {
        return database.soundDao().listLikedSounds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}