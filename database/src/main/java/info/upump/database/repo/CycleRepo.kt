package info.upump.database.repo

import android.content.Context
import info.upump.database.RepoActions
import info.upump.database.RoomDB
import info.upump.database.entities.CycleEntity

class CycleRepo private constructor(private val context: Context, db: RoomDB) :
    RepoActions<CycleEntity> {
    private val cycleDao = db.cycleDao()

    companion object {
        private var instance: CycleRepo? = null

        fun initialize(context: Context, db: RoomDB) {
            if (instance == null) {
                instance = CycleRepo(context, db)
            }
        }

        fun get(): RepoActions<CycleEntity> {
            return instance ?: throw IllegalStateException(" first need initialize repo")
        }
    }

    override fun getAll(): List<CycleEntity> {
        return cycleDao.getAllCycles()
    }

    override fun save(item: CycleEntity): CycleEntity {
        val newId  = cycleDao.save(item)
        return item.apply { _id = newId }
    }

    override fun getAllPersonal() : List<CycleEntity> {
       return cycleDao.getAllPersonalCycles()
    }

    override fun getAllDefault() : List<CycleEntity> {
        return cycleDao.getAllDefaultCycles()
    }
}