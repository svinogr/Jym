package info.upump.mycompose.models.entity

import info.upump.database.entities.WorkoutEntity
import info.upump.database.entities.WorkoutFullEntity

class Workout(
    var isWeekEven: Boolean = false,
    var isDefaultType: Boolean = false,
    var isTemplate: Boolean = false,
    var day: Day = Day.MONDAY,
    var exercises: List<Exercise> = ArrayList()
) : Entity() {

    companion object {
        fun mapFromDbEntity(workoutEntity: WorkoutEntity): Workout {
            val workout = Workout(
                isWeekEven = workoutEntity.week_even == 1,
                isDefaultType = workoutEntity.default_type == 1,
                isTemplate = workoutEntity.default_type == 1,
                day = Day.valueOf(workoutEntity.day!!),
                //TODO вставить настоящис список
                exercises = mutableListOf<Exercise>()
            )
            workout.title = workoutEntity.title
            workout.parentId = workoutEntity.parent_id!!
            workout.id = workoutEntity._id
            workout.comment = workoutEntity.comment!!
            workout.setStartDate(workoutEntity.start_date)
            workout.setFinishDate(workoutEntity.finish_date)

            return workout
        }

        fun mapToEntity(workout: Workout): WorkoutEntity {
            val workoutEntity = WorkoutEntity(workout.id)
            workoutEntity.title = workout.title
            workoutEntity.start_date = workout.startStringFormatDate
            workoutEntity.finish_date = workout.finishStringFormatDate
            workoutEntity.comment = workout.comment
            workoutEntity.day = workout.day.toString() //TODO внимание проверить правильность
            workoutEntity.default_type = if (workout.isDefaultType) 1 else 0
            workoutEntity.week_even = if (workout.isWeekEven) 1 else 0
            workoutEntity.template = if (workout.isTemplate) 1 else 0 // надо проверить
            workoutEntity.template = 0 //TODO  надо проверить
            workoutEntity.parent_id = workout.parentId

            return workoutEntity
        }

        fun mapFromFullDbEntity(entity: WorkoutFullEntity): Workout {
            val listEntityWorkout = entity.listExerciseEntity
            val listExercises = mutableListOf<Exercise>()

            listEntityWorkout.forEach(){
                listExercises.add(Exercise.mapFromFullDbEntity(it))
            }

            val workout = Workout(
                isWeekEven = entity.workoutEntity.week_even == 1,
                isDefaultType = entity.workoutEntity.default_type == 1,
                isTemplate = entity.workoutEntity.default_type == 1,
                day = Day.valueOf(entity.workoutEntity.day!!),
                exercises = listExercises
            )
            workout.title = entity.workoutEntity.title
            workout.parentId = entity.workoutEntity.parent_id!!
            workout.id = entity.workoutEntity._id
            workout.comment = entity.workoutEntity.comment!!
            workout.setStartDate(entity.workoutEntity.start_date)
            workout.setFinishDate(entity.workoutEntity.finish_date)

            return workout
        }
    }

    override fun toString(): String {
        return "Workout($title  --  $id  -- $parentId  isWeekEven=$isWeekEven, isDefaultType=$isDefaultType, isTemplate=$isTemplate, day=$day, exercises=$exercises) "
    }


}