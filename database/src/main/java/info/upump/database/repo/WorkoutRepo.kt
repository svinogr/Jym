package info.upump.database.repo

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Transaction
import info.upump.database.RepoActions
import info.upump.database.RepoActionsSpecific
import info.upump.database.RoomDB
import info.upump.database.entities.WorkoutEntity
import info.upump.database.entities.WorkoutFullEntity
import kotlinx.coroutines.flow.Flow

class WorkoutRepo(private val context: Context, db: RoomDB) :
    RepoActionsSpecific<WorkoutEntity, WorkoutFullEntity> {
    private val workoutDao = db.workoutDao()
    private val exerciseRepo = ExerciseRepo.get()

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: WorkoutRepo? = null

        fun initialize(context: Context, db: RoomDB) {
            if (instance == null) {
                instance = WorkoutRepo(context, db)
            }
        }

        fun get(): RepoActionsSpecific<WorkoutEntity, WorkoutFullEntity> {
            return instance ?: throw IllegalStateException(" first need initialize repo")
        }
    }

    override fun getFullEntityBy(id: Long): Flow<WorkoutFullEntity> {
        return workoutDao.getById(id)
    }

    override fun getAllFullEntityByParent(id: Long): Flow<List<WorkoutFullEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntity(): Flow<List<WorkoutFullEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<WorkoutEntity> {
        TODO("Not yet implemented")
    }

    override fun getAllPersonal(): Flow<List<WorkoutEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllDefault(): List<WorkoutEntity> {
        TODO("Not yet implemented")
    }

    override fun getBy(id: Long): Flow<WorkoutEntity> {
        TODO("Not yet implemented")
    }

    override fun getAllByParent(id: Long): Flow<List<WorkoutEntity>> {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteByParent(parentId: Long) {
        TODO("Not yet implemented")
    }

    override fun update(setsGet: WorkoutEntity): WorkoutEntity {
        TODO("Not yet implemented")
    }

    override fun save(item: WorkoutEntity): WorkoutEntity {
        TODO("Not yet implemented")
    }
    /*
        override fun getAll(): List<WorkoutEntity> {
            return workoutDao.getAllWorkouts()
        }

        override fun getAllPersonal(): Flow<List<WorkoutEntity>> {
            TODO("Not yet implemented")
        }

        override fun getAllDefault(): List<WorkoutEntity> {
            TODO("Not yet implemented")
        }

        override fun getBy(id: Long): Flow<WorkoutFullEntity> {
            return workoutDao.getById(id)
        }

        override fun getAllByParent(id: Long): Flow<List<WorkoutEntity>> {
            return workoutDao.getAllByParent(id)
        }

        @Transaction
        override fun delete(id: Long) {
            exerciseRepo.deleteByParent(id)
            workoutDao.delete(id)
        }

        @Transaction
        override fun deleteByParent(parentId: Long) {
            val listParentIdForNext = workoutDao.getListIdForNextByParent(parentId)
            workoutDao.deleteBy(parentId)
            listParentIdForNext.forEach{
                exerciseRepo.deleteByParent(it)
            }

        }


        override fun update(setsGet: WorkoutEntity): WorkoutEntity {
            TODO("Not yet implemented")
        }

        override fun save(item: WorkoutEntity): WorkoutEntity {
            if (item._id == 0L) {
                val id = workoutDao.save(item)
                item._id = id
            } else {
                workoutDao.update(item)
            }

            return item
        }*/
}