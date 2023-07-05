package info.upump.jym.db.repo

import android.content.Context
import info.upump.database.DatabaseApp
import info.upump.jym.db.RepoActions
import info.upump.jym.db.RoomDB
import info.upump.jym.db.entities.SetsEntity

class SetsRepo private constructor(private var context: Context, db: RoomDB) : RepoActions<SetsEntity> {
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

    override fun save(item: SetsEntity): SetsEntity {
        TODO("Not yet implemented")
    }
}