package info.upump.database.repo

import android.content.Context
import info.upump.database.RepoActions
import info.upump.database.RoomDB
import info.upump.database.entities.ExerciseDescriptionEntity
import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.ExerciseFullEntity
import info.upump.database.entities.SetsEntity
import kotlinx.coroutines.flow.Flow

class ExerciseRepo private constructor(private val context: Context, db: RoomDB) :
    RepoActions<ExerciseEntity> {
    private val exerciseDao = db.exerciseDao()

    companion object {
        private var instance: ExerciseRepo? = null

        fun initialize(context: Context, db: RoomDB) {
            if (instance == null) {
                instance = ExerciseRepo(context, db)
            }
        }

        fun get(): RepoActions<ExerciseEntity> {
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

    override fun test(id: Long):  Flow<List<ExerciseFullEntity>>{
        return exerciseDao.test(id)
    }

    override fun update(setsGet: ExerciseEntity): ExerciseEntity {
        TODO("Not yet implemented")
    }

    override fun save(item: ExerciseEntity): ExerciseEntity {
        TODO("Not yet implemented")
    }
}