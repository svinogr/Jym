package info.upump.jym.db.repo

import android.content.Context
import info.upump.database.DatabaseApp
import info.upump.jym.db.RepoActions
import info.upump.jym.db.RoomDB
import info.upump.jym.db.entities.CycleEntity

class CycleRepo private constructor(private val context: Context, db: RoomDB) : RepoActions<CycleEntity> {
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

}