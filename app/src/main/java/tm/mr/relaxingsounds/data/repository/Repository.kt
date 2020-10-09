package tm.mr.relaxingsounds.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.model.Sound

interface Repository {
    fun getSounds(
        categoryId: String? = null
    ): Flow<PagingData<Sound>>

    suspend fun getCategories(): Resource<List<Category>>
    suspend fun updateSound(sound: Sound)
    suspend fun getLikedSounds(): Resource<List<Sound>>
}
