package info.upump.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import info.upump.database.entities.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("select * from workouts")
    fun getAllWorkouts(): List<WorkoutEntity>
    @Query("select * from workouts where parent_id= :id")
    fun getAllByParent(id: Long): Flow<List<WorkoutEntity>>
    @Query("select * from workouts where _id= :id")
    fun getById(id: Long): Flow<WorkoutEntity>
    @Insert
    fun save(item: WorkoutEntity): Long
    @Update
    fun update(item: WorkoutEntity)
    @Query("delete from workouts where _id=:id")
    fun delete(id: Long)
    @Query("delete from workouts where parent_id=:parentId")
    fun deleteBy(parentId: Long)

}

