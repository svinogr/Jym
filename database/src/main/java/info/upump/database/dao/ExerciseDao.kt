package info.upump.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import info.upump.database.entities.ExerciseDescriptionEntity
import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.ExerciseFullEntity
import info.upump.database.entities.SetsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("select * from exercises")
    fun getAll(): List<ExerciseEntity>

    @Query("select * from exercises where parent_id= :id")
    fun getAllByParent(id: Long): Flow<List<ExerciseEntity>>
/*    @Query("select * from exercises join exercise_description On exercise_description._id = exercises.description_id where exercises.parent_id=:id")
    fun test(id: Long): Flow<Map<ExerciseEntity, ExerciseDescriptionEntity>>   */
    @Transaction
    @Query("select * from exercises where parent_id=:id")
    fun getFullEntityByParent(id: Long): Flow<List<ExerciseFullEntity>>
    @Transaction
    @Query("select * from exercises where _id= :id")
    fun getFullEntityBy(id: Long): Flow<ExerciseFullEntity>
    @Transaction
    @Query("select * from exercises where template = 1")
    fun getAllFullEntities(): Flow<List<ExerciseFullEntity>>
}