package info.upump.jym.db.dao

import androidx.room.Dao
import androidx.room.Query
import info.upump.jym.db.entities.ExerciseEntity

@Dao
interface ExerciseDao {
     @Query("select * from exercises")
     fun getAll(): List<ExerciseEntity>
}