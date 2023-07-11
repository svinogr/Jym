package info.upump.database

import androidx.room.Database
import androidx.room.RoomDatabase
import info.upump.database.dao.CycleDao
import info.upump.database.dao.ExerciseDao
import info.upump.database.dao.ExerciseDescriptionDao
import info.upump.database.dao.SetsDao
import info.upump.database.dao.WorkoutDao
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.ExerciseDescriptionEntity
import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.SetsEntity
import info.upump.database.entities.WorkoutEntity

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