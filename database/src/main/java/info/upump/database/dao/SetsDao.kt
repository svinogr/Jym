package info.upump.database.dao

import androidx.room.Dao
import androidx.room.Query
import info.upump.database.entities.SetsEntity

@Dao
interface SetsDao {
    @Query("select * from sets")
    fun getAll(): List<SetsEntity>

    @Query("select * from sets where parent_id= :id")
    fun getByParent(id: Long): List<SetsEntity>
   @Query("select * from sets where _id= :id")
    fun getBy(id: Long): SetsEntity
}