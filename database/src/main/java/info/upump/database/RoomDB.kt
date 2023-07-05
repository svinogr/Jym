package info.upump.jym.db

import androidx.room.Database
import androidx.room.RoomDatabase
import info.upump.jym.db.dao.CycleDao
import info.upump.jym.db.dao.ExerciseDao
import info.upump.jym.db.dao.ExerciseDescriptionDao
import info.upump.jym.db.dao.SetsDao
import info.upump.jym.db.dao.WorkoutDao
import info.upump.jym.db.entities.CycleEntity
import info.upump.jym.db.entities.ExerciseDescriptionEntity
import info.upump.jym.db.entities.ExerciseEntity
import info.upump.jym.db.entities.SetsEntity
import info.upump.jym.db.entities.WorkoutEntity


@Database(
    version = 1, entities = [
        CycleEntity::class,
        WorkoutEntity::class,
        ExerciseEntity::class,
        ExerciseDescriptionEntity::class,
        SetsEntity::class]
)
abstract class RoomDB : RoomDatabase() {
    abstract fun cycleDao(): CycleDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun exerciseDescriptionDao(): ExerciseDescriptionDao
    abstract fun setsDao(): SetsDao

    companion object {
        const val BASE_NAME = "jym.db"
        const val DB_PATH = "data/data/info.upump.jym/databases/$BASE_NAME"
    }
}