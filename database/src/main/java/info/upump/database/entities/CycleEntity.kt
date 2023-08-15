package info.upump.database.entities

import androidx.annotation.TransitionRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "cycles")
data class CycleEntity(
    @PrimaryKey(autoGenerate = true)
    var _id: Long,
) {
    @ColumnInfo
    var title: String = ""

    @ColumnInfo
    var comment: String? = ""

    @ColumnInfo
    var default_type: Int = 0

    @ColumnInfo
    var img: String? = ""

    @ColumnInfo
    var start_date: String = ""

    @ColumnInfo
    var finish_date: String = ""

    @ColumnInfo
    var default_img: String? = ""
    @Ignore
    override fun toString(): String {
        return "CycleEntity(_id=$_id, title='$title', comment=$comment, default_type=$default_type, img=$img, start_date='$start_date', finish_date='$finish_date', default_img=$default_img)"
    }


}