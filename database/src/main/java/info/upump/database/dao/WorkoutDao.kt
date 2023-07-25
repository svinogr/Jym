package info.upump.database.dao

import androidx.room.Dao
import androidx.room.Query
import info.upump.database.entities.WorkoutEntity

@Dao
interface WorkoutDao {
    @Query("select * from workouts")
    fun getAllWorkouts(): List<WorkoutEntity>

    @Query("select * from workouts where parent_id= :id")
    fun getAllByParent(id: Long): List<WorkoutEntity>
    @Query("select * from workouts where _id= :id")
    fun getById(id: Long): WorkoutEntity
}
