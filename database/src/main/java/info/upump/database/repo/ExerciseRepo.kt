package info.upump.database.repo

import android.content.Context
import info.upump.database.RepoActions
import info.upump.database.RepoActionsSpecific
import info.upump.database.RoomDB
import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.ExerciseFullEntity
import kotlinx.coroutines.flow.Flow

class ExerciseRepo private constructor(private val context: Context, db: RoomDB) :
    RepoActionsSpecific<ExerciseEntity, ExerciseFullEntity> {
    private val exerciseDao = db.exerciseDao()

    companion object {
        private var instance: ExerciseRepo? = null

        fun initialize(context: Context, db: RoomDB) {
            if (instance == null) {
                instance = ExerciseRepo(context, db)
            }
        }

        fun get(): RepoActionsSpecific<ExerciseEntity, ExerciseFullEntity> {
            return instance ?: throw IllegalStateException(" first need initialize repo")
        }
    }

    override fun getAll(): List<ExerciseEntity> {
        return exerciseDao.getAll()
    }

    override fun getAllPersonal(): Flow<List<ExerciseEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllDefault(): List<ExerciseEntity> {
        TODO("Not yet implemented")
    }

    override fun getBy(id: Long): Flow<ExerciseEntity> {
        TODO("Not yet implemented")
    }

    override fun getAllByParent(id: Long): Flow<List<ExerciseEntity>> {
        return exerciseDao.getAllByParent(id)
    }

    override fun deleteBy(id: Long) {
        TODO("Not yet implemented")
    }

    override fun update(setsGet: ExerciseEntity): ExerciseEntity {
        TODO("Not yet implemented")
    }

    override fun save(item: ExerciseEntity): ExerciseEntity {
        TODO("Not yet implemented")
    }

    override fun getFullEntityBy(id: Long): Flow<ExerciseFullEntity> {
        return exerciseDao.getFullEntityBy(id)
    }

    override fun getAllFullEntityByParent(id: Long): Flow<List<ExerciseFullEntity>> {
        return exerciseDao.getFullEntityByParent(id)
    }

    override fun getAllFullEntity(): Flow<List<ExerciseFullEntity>> {
              return exerciseDao.getAllFullEntities()
    }
}
