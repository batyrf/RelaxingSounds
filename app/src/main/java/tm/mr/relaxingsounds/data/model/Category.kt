package tm.mr.relaxingsounds.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class Category(
    @PrimaryKey val categoryId: String?,
    @ColumnInfo(name = "title") val title: String?
)