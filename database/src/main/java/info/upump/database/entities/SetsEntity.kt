package info.upump.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sets")
data class SetsEntity(
    @PrimaryKey(autoGenerate = true)
    var _id: Long
) {
    @ColumnInfo
    var comment: String? = ""

    @ColumnInfo
    var weight: Double? = 0.0

    @ColumnInfo
    var reps: Int? = 0

    @ColumnInfo
    var start_date: String = ""

    @ColumnInfo
    var finish_date: String = ""

    @ColumnInfo
    var parent_id: Long? = 0

    @ColumnInfo
    var past_set: Double? = 0.0
    override fun toString(): String {
        return "SetsEntity(_id=$_id, comment=$comment, weight=$weight, reps=$reps, start_date='$start_date', finish_date='$finish_date', parent_id=$parent_id, past_set=$past_set)"
    }


}
