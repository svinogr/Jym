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

    override fun getAllFullEntity(): Flow<List<CycleFullEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntityByParent(id: Long): Flow<List<CycleFullEntity>> {
        TODO("Not yet implemented")
    }


    override fun getAllFullEntityTemplate(): Flow<List<CycleFullEntity>> {
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

    override fun getAllFullEntityPersonal(): Flow<List<CycleFullEntity>> {
        return cycleDao.getAllPersonalCycles()
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