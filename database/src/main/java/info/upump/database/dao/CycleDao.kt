package info.upump.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import info.upump.database.entities.CycleEntity

@Dao
interface CycleDao {
    @Query("select * from cycles")
    fun getAllCycles(): List<CycleEntity>

    @Insert
    fun save(item: CycleEntity): Long

    @Query("select * from cycles where default_type = 0")
    fun getAllPersonalCycles(): List<CycleEntity>

    @Query("select * from cycles where default_type = 1")
    fun getAllDefaultCycles(): List<CycleEntity>
    @Query("select * from cycles where _id = :id")
    fun getBy(id: Long): CycleEntity

}