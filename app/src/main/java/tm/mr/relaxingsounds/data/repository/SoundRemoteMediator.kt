package tm.mr.relaxingsounds.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tm.mr.relaxingsounds.data.extension.ignoreNull
import tm.mr.relaxingsounds.data.local.SoundDatabase
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.data.remote.SoundsApi
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SoundRemoteMediator @Inject constructor(
    private val db: SoundDatabase,
    private val api: SoundsApi
) : RemoteMediator<Int, Sound>() {

    private var lastId: String? = null
    var categoryId: String? = null
    private var limit: Int = 10

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Sound>): MediatorResult {
        return when (loadType) {
            PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
            REFRESH, APPEND -> {
                withContext(Dispatchers.IO) {
                    try {
                        val result = api
                            .sounds(lastId, categoryId, limit)
                            .data.ignoreNull()
                        lastId = result.lastOrNull()?.id
                        db.soundDao().insertSounds(result)
                        MediatorResult.Success(endOfPaginationReached = result.isEmpty())
                    } catch (e: Exception) {
                        MediatorResult.Error(e)
                    }
                }
            }
        }
    }


}