package info.upump.database

import info.upump.database.dao.ExerciseDescriptionDao
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.ExerciseDescriptionEntity
import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.SetsEntity
import kotlinx.coroutines.flow.Flow

interface RepoActions<T> {
    fun getAll(): List<T>
    fun save(item: T): T
    fun getAllPersonal(): Flow<List<T>>
    fun getAllDefault(): List<T>
    fun getBy(id: Long): Flow<T>
    fun getAllByParent(id: Long): Flow<List<T>>
    fun update(setsGet: T): T

    fun test(id: Long): Flow<Map<T, ExerciseDescriptionEntity>>
}

