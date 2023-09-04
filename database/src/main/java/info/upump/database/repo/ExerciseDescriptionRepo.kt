package info.upump.database.repo

import android.content.Context
import info.upump.database.RepoActions
import info.upump.database.RoomDB
import info.upump.database.entities.ExerciseDescriptionEntity
import info.upump.database.entities.ExerciseFullEntity
import kotlinx.coroutines.flow.Flow

class ExerciseDescriptionRepo private constructor(private val context: Context, db: RoomDB):
    RepoActions<ExerciseDescriptionEntity> {
    private val exerciseDescriptionDao =  db.exerciseDescriptionDao()

    companion object {
        private var instance: ExerciseDescriptionRepo? = null

        fun initialize(context: Context, db: RoomDB) {
            if (instance == null) {
                instance = ExerciseDescriptionRepo(context, db)
            }
        }
        fun get(): RepoActions<ExerciseDescriptionEntity> {
            return instance ?: throw IllegalStateException(" first need initialize repo")
        }
    }

    override fun getAll(): List<ExerciseDescriptionEntity> {
      return exerciseDescriptionDao.getAll()
    }

    override fun getAllPersonal(): Flow<List<ExerciseDescriptionEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllDefault(): List<ExerciseDescriptionEntity> {
        TODO("Not yet implemented")
    }

    override fun getBy(id: Long): Flow<ExerciseDescriptionEntity> {
       return exerciseDescriptionDao.getBy(id)
    }

    override fun getAllByParent(id: Long): Flow<List<ExerciseDescriptionEntity>> {
        TODO("Not yet implemented")
    }

    override fun deleteBy(item: ExerciseDescriptionEntity): Long {
        TODO("Not yet implemented")
    }


    override fun update(setsGet: ExerciseDescriptionEntity): ExerciseDescriptionEntity {
        TODO("Not yet implemented")
    }

    fun getByParent(id: Long): ExerciseDescriptionEntity {
         TODO("Not yet implemented")
    }

    override fun save(item: ExerciseDescriptionEntity): ExerciseDescriptionEntity {
        TODO("Not yet implemented")
    }
}