package info.upump.mycompose.models.entity

import info.upump.database.entities.CycleEntity
import info.upump.database.entities.ExerciseEntity

class Exercise(
    var title: String = "",
    var typeMuscle: TypeMuscle? = null,
    var isDefaultType: Boolean = false,
    var isTemplate: Boolean = false,
    var setsList: List<Sets> = ArrayList(),
    var descriptionId: Long = 0,
    var exerciseDescription: ExerciseDescription? = null
) : Entity() {

    fun createInfo(): String {
        return "инфо"
    }

    override fun toString(): String {
        return "Exercise{" +
                "template" + isTemplate +
                ", desc " + descriptionId +
                ", typeMuscle=" + typeMuscle +
                ", defaultType=" + isDefaultType +
                ", setsList=" + setsList.size +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", parentId=" + parentId +
                '}'
    }

    companion object{
        fun mapFromDbEntity(entity: ExerciseEntity) : Exercise {
            val exercise = Exercise()
            exercise.id = entity._id
            exercise.parentId = entity.parent_id!!
            exercise.descriptionId = entity.description_id!!
          //TODO проверить

            return exercise
        }
    }
}