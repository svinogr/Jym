package info.upump.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    var _id: Long
) {
    @ColumnInfo
    var comment: String? = ""

    @ColumnInfo
    var description_id: Long? = 0

    @ColumnInfo
    var type_exercise: String? = ""

    @ColumnInfo
    var default_type: Int = 0

    @ColumnInfo()
    var template: Int = 0

    @ColumnInfo
    var start_date: String = ""

    @ColumnInfo
    var finish_date: String = ""

    @ColumnInfo
    var parent_id: Long? = 0
    override fun toString(): String {
        return "ExerciseEntity(_id=$_id, comment=$comment, description_id=$description_id, type_exercise=$type_exercise, default_type=$default_type, template=$template, start_date='$start_date', finish_date='$finish_date', parent_id=$parent_id)"
    }


}