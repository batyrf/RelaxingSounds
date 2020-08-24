package tm.mr.relaxingsounds.data.local

import androidx.paging.PagingSource
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Sound

@Dao
interface SoundDao {

    @Query("SELECT * FROM sounds ORDER BY id ASC")
    fun listSounds(): PagingSource<Int, Sound>

    @Query("SELECT * FROM sounds WHERE isLike = 1")
    fun listLikedSounds(): Observable<List<Sound>>

    @Query("SELECT * FROM sounds WHERE categoryId IN (:categoryId)")
    fun listSoundsByCategory(categoryId: String): PagingSource<Int, Sound>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertSounds(sounds: List<Sound>): Completable

    @Update
    fun updateSound(sound: Sound): Completable

    @Query("SELECT * FROM categories")
    fun listCategories(): Observable<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: List<Category>): Completable

}