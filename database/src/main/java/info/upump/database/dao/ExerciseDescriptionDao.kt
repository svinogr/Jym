package info.upump.database.dao

import androidx.room.Dao
import androidx.room.Query
import info.upump.database.entities.ExerciseDescriptionEntity

@Dao
interface ExerciseDescriptionDao {
    @Query("select * from exercise_description")
    fun getAll(): List<ExerciseDescriptionEntity>
}