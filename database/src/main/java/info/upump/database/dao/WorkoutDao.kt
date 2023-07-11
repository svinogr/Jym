package info.upump.database.dao

import androidx.room.Dao
import androidx.room.Query
import info.upump.database.entities.WorkoutEntity

@Dao
interface WorkoutDao {
    @Query("select * from workouts")
    fun getAllWorkouts(): List<WorkoutEntity>
}
