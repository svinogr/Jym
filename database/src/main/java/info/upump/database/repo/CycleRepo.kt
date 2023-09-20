package info.upump.database.repo

import android.util.Log
import androidx.room.Transaction
import info.upump.database.RepoActionsSpecific
import info.upump.database.RoomDB
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntity
import info.upump.database.entities.CycleFullEntityWithWorkouts
import kotlinx.coroutines.flow.Flow

class CycleRepo private constructor(db: RoomDB) :
    RepoActionsSpecific<CycleEntity, CycleFullEntityWithWorkouts> {
    private val cycleDao = db.cycleDao()
    private val workoutRepo = WorkoutRepo.get()

    companion object {
        private var instance: CycleRepo? = null

        fun initialize(db: RoomDB) {
                instance = CycleRepo(db)
        }

        fun get(): RepoActionsSpecific<CycleEntity, CycleFullEntityWithWorkouts> {
            return instance ?: throw IllegalStateException("first need initialize repo")
        }
    }

    override fun getFullEntityBy(id: Long): Flow<CycleFullEntityWithWorkouts> {
        return cycleDao.getFullBy(id)
    }

    override fun getAllFullEntity(): Flow<List<CycleFullEntityWithWorkouts>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntityByParent(id: Long): Flow<List<CycleFullEntityWithWorkouts>> {
        TODO("Not yet implemented")
    }


    override fun getAllFullEntityTemplate(): Flow<List<CycleFullEntityWithWorkouts>> {
        return cycleDao.getAllTemplate()
    }

    override fun save(item: CycleEntity): CycleEntity {
        Log.d("save", "id = ${item._id}")
        if (item._id == 0L) {
            val newId = cycleDao.save(item)
            item.apply { _id = newId }
        } else cycleDao.update(item)

        return item
    }

    override fun getAllFullEntityPersonal(): Flow<List<CycleFullEntityWithWorkouts>> {
        return cycleDao.getAllPersonalCycles()
    }

     fun getAllFullestEntityPersonal(): Flow<List<CycleFullEntity>> {
        return cycleDao.getAllFullPersonalCycles()
    }

    override fun getAllFullEntityDefault(): Flow<List<CycleFullEntityWithWorkouts>> {
        return cycleDao.getAllTemplate()
    }

    @Transaction
    override fun delete(id: Long) {
        cycleDao.delete(id)
        workoutRepo.deleteByParent(id)

    }

    override fun deleteByParent(parentId: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteChilds(parentId: Long) {
        Log.d("clean", "$parentId")
        workoutRepo.deleteByParent(parentId)
    }

    override fun update(item: CycleEntity): CycleEntity {
        cycleDao.update(item)
        return item
    }
}