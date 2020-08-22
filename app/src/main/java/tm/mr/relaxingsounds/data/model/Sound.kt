package tm.mr.relaxingsounds.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Sounds")
data class Sound(
    @PrimaryKey val id: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "isLike") val isLike: Boolean?,
    @ColumnInfo(name = "categoryId") val categoryId: String?
)