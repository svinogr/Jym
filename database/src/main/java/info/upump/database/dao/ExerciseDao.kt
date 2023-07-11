package info.upump.database.dao

import androidx.room.Dao
import androidx.room.Query
import info.upump.database.entities.ExerciseEntity

@Dao
interface ExerciseDao {
     @Query("select * from exercises")
     fun getAll(): List<ExerciseEntity>
}