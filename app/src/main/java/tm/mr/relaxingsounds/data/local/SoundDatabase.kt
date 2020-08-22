package tm.mr.relaxingsounds.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Sound

@Database(entities = [Sound::class, Category::class], version = 1)
abstract class SoundDatabase : RoomDatabase() {

    abstract fun soundDao(): SoundDao

}