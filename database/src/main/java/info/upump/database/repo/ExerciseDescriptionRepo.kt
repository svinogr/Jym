package info.upump.database.repo

import android.content.Context
import info.upump.database.RepoActions
import info.upump.database.RoomDB
import info.upump.database.entities.ExerciseDescriptionEntity

class ExerciseDescriptionRepo private constructor(private val context: Context, db: RoomDB):
    RepoActions<ExerciseDescriptionEntity> {
    private val exerciseDescriptionRepo =  db.exerciseDescriptionDao()

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
      return exerciseDescriptionRepo.getAll()
    }

    override fun getAllPersonal(): List<ExerciseDescriptionEntity> {
        TODO("Not yet implemented")
    }

    override fun getAllDefault(): List<ExerciseDescriptionEntity> {
        TODO("Not yet implemented")
    }

    override fun getBy(id: Long): ExerciseDescriptionEntity {
        TODO("Not yet implemented")
    }

    override fun getAllByParent(id: Long): List<ExerciseDescriptionEntity> {
        TODO("Not yet implemented")
    }

    override fun save(item: ExerciseDescriptionEntity): ExerciseDescriptionEntity {
        TODO("Not yet implemented")
    }
}