package info.upump.database.repo

import android.content.Context
import info.upump.database.RepoActions
import info.upump.database.RoomDB
import info.upump.database.entities.ExerciseEntity

class ExerciseRepo private constructor(private val context: Context, db: RoomDB) :
    RepoActions<ExerciseEntity> {
    private val exerciseRepo = db.exerciseDao()

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
        return exerciseRepo.getAll()
    }

    override fun getAllPersonal(): List<ExerciseEntity> {
        TODO("Not yet implemented")
    }

    override fun getAllDefault(): List<ExerciseEntity> {
        TODO("Not yet implemented")
    }

    override fun save(item: ExerciseEntity): ExerciseEntity {
        TODO("Not yet implemented")
    }

}