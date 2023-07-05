package info.upump.jym.db.dao

import androidx.room.Dao
import androidx.room.Query
import info.upump.jym.db.entities.ExerciseDescriptionEntity

@Dao
interface ExerciseDescriptionDao {
    @Query("select * from exercise_description")
    fun getAll(): List<ExerciseDescriptionEntity>
}