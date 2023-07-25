package info.upump.database.dao

import androidx.room.Dao
import androidx.room.Query
import info.upump.database.entities.ExerciseEntity

@Dao
interface ExerciseDao {
     @Query("select * from exercises")
     fun getAll(): List<ExerciseEntity>

     @Query("select * from exercises where parent_id= :id")
     fun getAllByParent(id: Long): List<ExerciseEntity>
}