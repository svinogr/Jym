package info.upump.database.repo

import android.content.Context
import info.upump.database.DatabaseApp
import info.upump.database.RepoActions
import info.upump.database.RoomDB
import info.upump.database.entities.SetsEntity

class SetsRepo private constructor(private var context: Context, db: RoomDB) :
    RepoActions<SetsEntity> {
    private val setsDao = DatabaseApp.db.setsDao()

    companion object {
        private var instance: SetsRepo? = null

        fun initialize(context: Context, db: RoomDB) {
            if (instance == null) {
                instance = SetsRepo(context, db)
            }
        }

        fun get(): RepoActions<SetsEntity> {
            return instance ?: throw IllegalStateException(" first need initialize repo")
        }
    }

    override fun getAll(): List<SetsEntity> {
        return setsDao.getAll()
    }

    override fun getAllPersonal(): List<SetsEntity> {
        TODO("Not yet implemented")
    }

    override fun getAllDefault(): List<SetsEntity> {
        TODO("Not yet implemented")
    }

    override fun getBy(id: Long): SetsEntity {
        return setsDao.getBy(id)
    }

    override fun getAllByParent(id: Long): List<SetsEntity> {
      return setsDao.getByParent(id)
    }

    override fun save(item: SetsEntity): SetsEntity {
        TODO("Not yet implemented")
    }
}