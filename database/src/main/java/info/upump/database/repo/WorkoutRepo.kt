package info.upump.database.repo

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Query
import info.upump.database.RepoActions
import info.upump.database.RoomDB
import info.upump.database.entities.ExerciseDescriptionEntity
import info.upump.database.entities.ExerciseFullEntity
import info.upump.database.entities.WorkoutEntity
import kotlinx.coroutines.flow.Flow

class WorkoutRepo(private val context: Context, db: RoomDB) : RepoActions<WorkoutEntity> {
    private val workoutDao = db.workoutDao()

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: WorkoutRepo? = null

        fun initialize(context: Context, db: RoomDB) {
            if (instance == null) {
                instance = WorkoutRepo(context, db)
            }
        }

        fun get(): RepoActions<WorkoutEntity> {
            return instance ?: throw IllegalStateException(" first need initialize repo")
        }
    }

    override fun getAll(): List<WorkoutEntity> {
        return workoutDao.getAllWorkouts()
    }

    override fun getAllPersonal(): Flow<List<WorkoutEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllDefault(): List<WorkoutEntity> {
        TODO("Not yet implemented")
    }

    override fun getBy(id: Long): Flow<WorkoutEntity> {
      return workoutDao.getById(id)
    }

    override fun getAllByParent(id: Long): Flow<List<WorkoutEntity>>{
        return workoutDao.getAllByParent(id)
    }

    override fun deleteBy(item: WorkoutEntity): Long {
        TODO("Not yet implemented")
    }

    override fun update(setsGet: WorkoutEntity): WorkoutEntity {
        TODO("Not yet implemented")
    }

    override fun save(item: WorkoutEntity): WorkoutEntity {
        TODO("Not yet implemented")
    }
}