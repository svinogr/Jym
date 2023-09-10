package info.upump.database.repo

import android.content.Context
import android.util.Log
import androidx.room.Transaction
import info.upump.database.RepoActionsSpecific
import info.upump.database.RoomDB
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntity
import kotlinx.coroutines.flow.Flow

class CycleRepo private constructor(private val context: Context, db: RoomDB) :
    RepoActionsSpecific<CycleEntity, CycleFullEntity> {
    private val cycleDao = db.cycleDao()
    private val workoutRepo = WorkoutRepo.get()

    companion object {
        private var instance: CycleRepo? = null

        fun initialize(context: Context, db: RoomDB) {
            if (instance == null) {
                instance = CycleRepo(context, db)
            }
        }

        fun get(): RepoActionsSpecific<CycleEntity, CycleFullEntity> {
            return instance ?: throw IllegalStateException("first need initialize repo")
        }
    }

    override fun getFullEntityBy(id: Long): Flow<CycleFullEntity> {
        return cycleDao.getFullBy(id)
    }

    override fun getAllFullEntityByParent(id: Long): Flow<List<CycleFullEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntity(): Flow<List<CycleFullEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<CycleEntity> {
        return cycleDao.getAllCycles()
    }

    override fun save(item: CycleEntity): CycleEntity {
        Log.d("save", "id = ${item._id}")
        if (item._id == 0L) {
            val newId = cycleDao.save(item)
            item.apply { _id = newId }
        } else cycleDao.update(item)

        return item
    }

    override fun getAllPersonal(): Flow<List<CycleEntity>> {
        return cycleDao.getAllPersonalCycles()
    }

    override fun getAllDefault(): List<CycleEntity> {
        return cycleDao.getAllDefaultCycles()
    }

    override fun getBy(id: Long): Flow<CycleEntity> {
        return cycleDao.getBy(id)
    }

    override fun getAllByParent(id: Long): Flow<List<CycleEntity>> {
        TODO("Not yet implemented")
    }

    @Transaction
    override fun delete(id: Long) {
        cycleDao.delete(id)
        workoutRepo.deleteByParent(id)

    }


    override fun deleteByParent(parentId: Long) {
        TODO("Not yet implemented")
    }

    @Transaction
    fun exp(id: Long): Flow<CycleFullEntity> {
        return cycleDao.exp(id)
    }

    override fun update(setsGet: CycleEntity): CycleEntity {
        TODO("Not yet implemented")
    }
}