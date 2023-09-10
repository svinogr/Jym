package info.upump.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseFullEntity(
    @Embedded
    val exerciseEntity: ExerciseEntity,
    @Relation(parentColumn = "description_id", entityColumn = "_id")
    val exerciseDescriptionEntity: ExerciseDescriptionEntity,
    @Relation(parentColumn = "_id", entityColumn = "parent_id", entity = SetsEntity::class)
    val listSetsEntity: List<SetsEntity>
) {

    constructor() : this(ExerciseEntity(), ExerciseDescriptionEntity(), listOf())
}

data class ExerciseFullEntityWithoutDescription(
    @Embedded
    val exerciseEntity: ExerciseEntity,
    @Relation(parentColumn = "_id", entityColumn = "parent_id", entity = SetsEntity::class)
    val listSetsEntity: List<SetsEntity>
) {
    constructor() : this(ExerciseEntity(), listOf())
}


