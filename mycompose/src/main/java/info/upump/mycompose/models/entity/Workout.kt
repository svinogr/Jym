package info.upump.mycompose.models.entity

import info.upump.database.entities.WorkoutEntity
import kotlinx.coroutines.flow.map

class Workout(
    var isWeekEven: Boolean = false,
    var isDefaultType: Boolean = false,
    var isTemplate: Boolean = false,
    var day: Day? = null,
    var exercises: List<Exercise> = ArrayList()
) : Entity() {

    override fun toString(): String {
        return "Workout{" +
                "id=" + id +
                "com " + comment +
                ", title='" + title + '\'' +
                ", weekEven=" + isWeekEven +
                ", Day=" + day +
                ", defaultType=" + isDefaultType +
                ", cycle Id= " + parentId +
                ", exercises Size=" + exercises.size +
                '}'
    }

    companion object {
        fun mapFromDbEntity(workoutEntity: WorkoutEntity): Workout {
          /*  return workoutEntity.map {
                Workout(
                    title = it.title,
                    isWeekEven = it.week_even == 1,
                    isDefaultType = it.default_type == 1,
                    isTemplate = it.default_type == 1,
                    day = Day.valueOf(it.day!!),
                    //TODO вставить настоящис список
                    exercises = listOf<Exercise>()
                ).apply {
                    id = it._id
                    comment = it.comment
                }*/
            val workout = Workout(
                isWeekEven = workoutEntity.week_even == 1,
                isDefaultType = workoutEntity.default_type == 1,
                isTemplate = workoutEntity.default_type == 1,
                day = Day.valueOf(workoutEntity.day!!),
                //TODO вставить настоящис список
                exercises = listOf<Exercise>()
            )
            workout.title = workoutEntity.title
            workout.id = workoutEntity._id
            workout.comment = workoutEntity.comment!!

            return workout
        }
    }
}