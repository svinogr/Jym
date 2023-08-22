package info.upump.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseFullEntity(
    @Embedded
    val exercise: ExerciseEntity,
    @Relation(parentColumn = "description_id", entityColumn = "_id")
    val exerciseDescriptionEntity: ExerciseDescriptionEntity,
    @Relation(parentColumn = "_id", entityColumn = "parent_id", entity = SetsEntity::class)
    val listSet: List<SetsEntity>
)

