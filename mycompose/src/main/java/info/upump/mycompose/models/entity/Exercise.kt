package info.upump.mycompose.models.entity

import android.util.Log
import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.ExerciseFullEntity

class Exercise(
    var typeMuscle: TypeMuscle = TypeMuscle.CALVES,
    var isDefaultType: Boolean = false,
    var isTemplate: Boolean = false,
    var setsList: MutableList<Sets> = ArrayList(),
    var descriptionId: Long = 0,
    var exerciseDescription: ExerciseDescription? = null,

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
                ", parentId=" + parentId + "descr" + exerciseDescription.toString()
                '}'
    }

    companion object{
        fun mapFromDbEntity(entity: ExerciseEntity) : Exercise {
            val exercise = Exercise()
            exercise.id = entity._id
            exercise.parentId = entity.parent_id!!
            exercise.descriptionId = entity.description_id!!
            exercise.typeMuscle = TypeMuscle.valueOf(entity.type_exercise!!)
            exercise.isTemplate = entity.template == 1
          //TODO проверить

            return exercise
        }

        fun mapFromFullDbEntity(entity: ExerciseFullEntity): Exercise {
            val exercise = mapFromDbEntity(entity.exercise)
            val exerciseDescription = ExerciseDescription.mapFromDbEntity(entity.exerciseDescriptionEntity)
            val listSets = mutableListOf<Sets>()
            Log.d("mapFromFullDbEntity", "-------- exe ${exercise.id}")
            entity.listSet.forEach{
                val set = Sets.mapFromDbEntity(it)
                Log.d("mapFromFullDbEntity", "$it")
                listSets.add(set)
            }
            Log.d("mapFromFullDbEntity", "-------- exe end}")
            exercise.exerciseDescription = exerciseDescription
            exercise.setsList = listSets

            return exercise
        }
    }
}