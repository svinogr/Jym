package info.upump.mycompose.models.entity

import info.upump.database.entities.WorkoutEntity

class Workout(
    var isWeekEven: Boolean = false,
    var isDefaultType: Boolean = false,
    var isTemplate: Boolean = false,
    var day: Day = Day.MONDAY,
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

        fun mapToEntity(workout: Workout): WorkoutEntity {
            val workoutEntity = WorkoutEntity(workout.id)
            workoutEntity.title = workout.title.orEmpty()
            workoutEntity.start_date = workout.startStringFormatDate
            workoutEntity.finish_date = workout.finishStringFormatDate
            workoutEntity.comment = workout.comment
            workoutEntity.day = workout.day.toString() //TODO внимание проверить правильность
            workoutEntity.default_type = if (workout.isDefaultType) 1 else 0
            workoutEntity.week_even = if (workout.isWeekEven) 1 else 0
           // workoutEntity.template = if (workout.isTemplate) 1 else 0 // надо проверить
            workoutEntity.template = 0 //TODO  надо проверить

            return workoutEntity
        }

        fun copy(workout: Workout): Workout {
            return Workout().apply {
                id = workout.id
                title = workout.title
                isDefaultType = workout.isDefaultType
                day = workout.day
                isWeekEven = workout.isWeekEven
                isTemplate = workout.isTemplate
                startDate = workout.startDate
                finishDate = workout.finishDate
                comment = workout.comment
                parentId = workout.parentId
            }
        }
    }
}