package info.upump.mycompose.models.entity

import info.upump.database.entities.WorkoutEntity

class Workout(
    var title: String?,
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
            val workout = Workout(
                title = workoutEntity.title,
                isWeekEven = workoutEntity.week_even == 1,
                isDefaultType = workoutEntity.default_type == 1,
                isTemplate = workoutEntity.default_type == 1,
                day = Day.valueOf(workoutEntity.day!!),
                //TODO вставить настоящис список
                exercises = listOf<Exercise>()
            )
            workout.id = workoutEntity._id

            return workout
        }
    }
}