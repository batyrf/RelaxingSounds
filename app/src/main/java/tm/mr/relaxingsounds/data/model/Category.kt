package tm.mr.relaxingsounds.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class Category(
    @NonNull @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String?
)