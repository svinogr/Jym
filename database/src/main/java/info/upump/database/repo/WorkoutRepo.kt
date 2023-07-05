package info.upump.jym.db.repo

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.RoomDatabase
import info.upump.database.DatabaseApp
import info.upump.jym.db.RepoActions
import info.upump.jym.db.RoomDB
import info.upump.jym.db.entities.WorkoutEntity

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

    override fun save(item: WorkoutEntity): WorkoutEntity {
        TODO("Not yet implemented")
    }
}