package info.upump.database.repo

import android.content.Context
import androidx.room.Transaction
import info.upump.database.RepoActionsSpecific
import info.upump.database.RoomDB
import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.ExerciseFullEntity
import kotlinx.coroutines.flow.Flow

class ExerciseRepo private constructor(private val context: Context, db: RoomDB) :
    RepoActionsSpecific<ExerciseEntity, ExerciseFullEntity> {
    private val exerciseDao = db.exerciseDao()
    private val setsRepo = SetsRepo.get()

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


    override fun getAllFullEntityByParent(parentId: Long): Flow<List<ExerciseFullEntity>> {
        return exerciseDao.getAllByParent(parentId)
    }

    override fun delete(id: Long) {
        exerciseDao.deleteBy(id)
    }

    @Transaction
    override fun deleteByParent(parentId: Long) {
        val listParentIdForNext = exerciseDao.getListIdForNextByParent(parentId)
        exerciseDao.deleteByParent(parentId)
        listParentIdForNext.forEach {
            setsRepo.deleteByParent(it)
        }
    }


    override fun update(item: ExerciseEntity): ExerciseEntity {
        TODO("Not yet implemented")
    }

    override fun save(item: ExerciseEntity): ExerciseEntity {
        TODO("Not yet implemented")
    }

    override fun getFullEntityBy(id: Long): Flow<ExerciseFullEntity> {
        return exerciseDao.getFullEntityBy(id)
    }


    override fun getAllFullEntity(): Flow<List<ExerciseFullEntity>> {
        return exerciseDao.getAllFullEntities()
    }

    override fun getAllFullEntityTemplate(): Flow<List<ExerciseFullEntity>> {
          return exerciseDao.getAllFullTemplateEntity()

    }

    override fun getAllFullEntityPersonal(): Flow<List<ExerciseFullEntity>> {
        TODO("Not yet implemented")
    }
}
