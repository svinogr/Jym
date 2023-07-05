package info.upump.jym.db.dao

import androidx.room.Dao
import androidx.room.Query
import info.upump.jym.db.entities.SetsEntity

@Dao
interface SetsDao {
    @Query("select * from sets")
    fun getAll(): List<SetsEntity>
}