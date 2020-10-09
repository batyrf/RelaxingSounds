package tm.mr.relaxingsounds.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import tm.mr.relaxingsounds.data.extension.ignoreNull
import tm.mr.relaxingsounds.data.local.SoundDatabase
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Resource
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
    ): Flow<PagingData<Sound>> {
        remoteMediator.categoryId = categoryId
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                categoryId?.let {
                    database.soundDao().listSoundsByCategory(it)
                } ?: database.soundDao().listSounds()
            }
        ).flow
    }

    override suspend fun getCategories(): Resource<List<Category>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.categories().data.ignoreNull()
                database.soundDao().insertCategories(result)
                Resource.success(result)
            } catch (e: Exception) {
                val dbResult = database.soundDao().listCategories()
                Resource.success(dbResult)
            }
        }
    }

    override suspend fun updateSound(sound: Sound) {
        return database.soundDao().updateSound(sound)
    }

    override suspend fun getLikedSounds(): Resource<List<Sound>> {
        return withContext(Dispatchers.IO) {
            database.soundDao().listLikedSounds()
            try {
                val result = database.soundDao().listLikedSounds()
                Resource.success(result)
            } catch (e: Exception) {
                Resource.error(e.localizedMessage)
            }
        }
    }

}