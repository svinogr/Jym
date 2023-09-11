package info.upump.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CycleDao {
    @Query("select * from cycles")
    fun getAllCycles(): List<CycleEntity>

    @Insert()
    fun save(item: CycleEntity): Long

    @Update()
    fun update(item: CycleEntity): Int
    @Transaction
    @Query("select * from cycles where default_type = 0")
    fun getAllPersonalCycles(): Flow<List<CycleFullEntity>>
    @Transaction
    @Query("select * from cycles where default_type = 1")
    fun getAllTemplate(): Flow<List<CycleFullEntity>>

/*    @Query("select * from cycles where _id=:id")
    fun getBy(id: Long): Flow<CycleFullEntity>*/
@Transaction
    @Query("select * from cycles where _id=:id")
    fun getFullBy(id: Long): Flow<CycleFullEntity>

    @Query("delete from cycles where _id=:id")
    fun delete(id: Long)
    @Transaction
    @Query("select * from cycles where _id=:id")
    fun exp(id: Long): Flow<CycleFullEntity>

}