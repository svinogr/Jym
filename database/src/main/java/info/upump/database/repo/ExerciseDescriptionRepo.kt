package info.upump.jym.db.repo

import android.content.Context
import info.upump.database.DatabaseApp
import info.upump.jym.db.RepoActions
import info.upump.jym.db.RoomDB
import info.upump.jym.db.entities.ExerciseDescriptionEntity

class ExerciseDescriptionRepo private constructor(private val context: Context, db: RoomDB): RepoActions<ExerciseDescriptionEntity> {
    private val exerciseDescriptionRepo =  db.exerciseDescriptionDao()

    companion object {
        private var instance: ExerciseDescriptionRepo? = null

        fun initialize(context: Context, db: RoomDB) {
            if (instance == null) {
                instance = ExerciseDescriptionRepo(context, db)
            }
        }
        fun get(): RepoActions<ExerciseDescriptionEntity> {
            return ExerciseDescriptionRepo.instance ?: throw IllegalStateException(" first need initialize repo")
        }
    }


    override fun getAll(): List<ExerciseDescriptionEntity> {
      return exerciseDescriptionRepo.getAll()
    }

    override fun save(item: ExerciseDescriptionEntity): ExerciseDescriptionEntity {
        TODO("Not yet implemented")
    }
}