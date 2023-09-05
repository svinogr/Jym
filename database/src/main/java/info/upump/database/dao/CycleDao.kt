package info.upump.database.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import info.upump.database.entities.CycleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CycleDao {
    @Query("select * from cycles")
    fun getAllCycles(): List<CycleEntity>

    @Insert()
    fun save(item: CycleEntity): Long

    @Update()
    fun update(item: CycleEntity): Int

    @Query("select * from cycles where default_type = 0")
    fun getAllPersonalCycles(): Flow<List<CycleEntity>>

    @Query("select * from cycles where default_type = 1")
    fun getAllDefaultCycles(): List<CycleEntity>

    @Query("select * from cycles where _id= :id")
    fun getBy(id: Long): Flow<CycleEntity>

    @Query("delete from cycles where _id= :id")
    fun delete(id: Long)

}