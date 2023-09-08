package info.upump.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutFullEntity(
    @Embedded
    val workout: WorkoutEntity,
    @Relation(parentColumn = "_id", entityColumn = "parent_id", entity = ExerciseEntity::class)
    val listExercise: List<ExerciseEntity>


)

data class CycleFullEntity2(
    @Embedded
    val cycle: CycleEntity,
    @Relation(parentColumn = "_id", entityColumn = "parent_id", entity = WorkoutEntity::class)
    val listWorkouts: List<WorkoutEntity>,

    @Relation(parentColumn = "_id", entityColumn = "parent_id", entity = ExerciseEntity::class)
    val listExercise: List<ExerciseEntity>

)