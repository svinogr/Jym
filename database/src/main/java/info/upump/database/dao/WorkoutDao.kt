package info.upump.jym.db.dao

import androidx.room.Dao
import androidx.room.Query
import info.upump.jym.db.entities.WorkoutEntity

@Dao
interface WorkoutDao {
    @Query("select * from workouts")
    fun getAllWorkouts(): List<WorkoutEntity>
}
