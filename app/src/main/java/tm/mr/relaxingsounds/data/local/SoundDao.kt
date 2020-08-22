package tm.mr.relaxingsounds.data.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Sound

@Dao
interface SoundDao {

    @Query("SELECT * FROM sounds")
    fun listSounds(): Observable<List<Sound>>

    @Query("SELECT * FROM sounds WHERE categoryId IN (:categoryId)")
    fun listSoundsByCategory(categoryId: String): Observable<List<Sound>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSound(sound: Sound): Completable

    @Query("SELECT * FROM categories")
    fun listCategories(): Observable<List<Category>>

}