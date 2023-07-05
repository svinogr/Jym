package info.upump.jym.db.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import info.upump.jym.db.entities.CycleEntity

@Dao
interface CycleDao {
    @Query("select * from cycles")
    fun getAllCycles(): List<CycleEntity>

    @Insert
    fun save(item: CycleEntity): Long
}