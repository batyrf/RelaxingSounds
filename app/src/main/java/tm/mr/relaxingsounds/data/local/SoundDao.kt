package tm.mr.relaxingsounds.data.local

import androidx.paging.PagingSource
import androidx.room.*
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Sound

@Dao
interface SoundDao {

    @Query("SELECT * FROM sounds ORDER BY id ASC")
    fun listSounds(): PagingSource<Int, Sound>

    @Query("SELECT * FROM sounds WHERE isLike = 1")
    suspend fun listLikedSounds(): List<Sound>

    @Query("SELECT * FROM sounds WHERE categoryId IN (:categoryId)")
    fun listSoundsByCategory(categoryId: String): PagingSource<Int, Sound>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSounds(sounds: List<Sound>)

    @Update
    suspend fun updateSound(sound: Sound)

    @Query("SELECT * FROM categories")
    suspend fun listCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

}