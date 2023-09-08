package info.upump.database.repo

import android.content.Context
import android.util.Log
import androidx.room.Transaction
import info.upump.database.RepoActions
import info.upump.database.RoomDB
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntity2
import kotlinx.coroutines.flow.Flow

class CycleRepo private constructor(private val context: Context, db: RoomDB) :
    RepoActions<CycleEntity> {
    private val cycleDao = db.cycleDao()
    private val workoutRepo = WorkoutRepo.get()

    companion object {
        private var instance: CycleRepo? = null

        fun initialize(context: Context, db: RoomDB) {
            if (instance == null) {
                instance = CycleRepo(context, db)
            }
        }

        fun get(): RepoActions<CycleEntity> {
            return instance ?: throw IllegalStateException("first need initialize repo")
        }
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
    override fun deleteBy(id: Long) {
        Log.d("dele cycle", "id")
        cycleDao.delete(id)

        //     workoutRepo.deleteByParent(id)

    }

    @Transaction
    fun exp(id: Long): Flow<CycleFullEntity2> {
        return cycleDao.exp(id)
    }

    override fun update(setsGet: CycleEntity): CycleEntity {
        TODO("Not yet implemented")
    }
}