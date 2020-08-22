package tm.mr.relaxingsounds.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Sound

@Database(entities = [Sound::class, Category::class], version = 1)
abstract class SoundDatabase : RoomDatabase() {

    abstract fun soundDao(): SoundDao

    companion object {

        @Volatile
        private var INSTANCE: SoundDatabase? = null

        fun getInstance(context: Context): SoundDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SoundDatabase::class.java, "RelaxingSounds.db"
            )
                .build()
    }
}