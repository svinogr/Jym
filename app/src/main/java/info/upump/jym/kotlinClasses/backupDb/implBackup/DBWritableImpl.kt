package info.upump.jym.kotlinClasses.backupDb.implBackup

import android.content.Context
import info.upump.jym.bd.*
import info.upump.jym.entity.Cycle
import info.upump.jym.entity.Exercise
import info.upump.jym.entity.ExerciseDescription
import info.upump.jym.kotlinClasses.backupDb.DBWritable

class DBWritableImpl(val context: Context) : DBWritable {
    override fun writeToMail() {
        println("do write to email")
    }

    override fun writeToDB(from: List<Cycle>) {
        if (from.isEmpty()) return
        println("razmer vh ${from.size}")
        val exerciseDaoBackup = ExerciseDescriptionDao.getInstance(context, null)
        val oldExerciseDescriptionList = exerciseDaoBackup.all
        val map = hashMapOf<ExerciseDescription, MutableList<Exercise>>()

        if (oldExerciseDescriptionList != null) {
            for (exersciseDescription in oldExerciseDescriptionList) {
                val list = map[exersciseDescription]

                if (list == null) {
                    map[exersciseDescription] = arrayListOf()
                }
            }
        }

        for (cycle in from) {

            for (workout in cycle.workoutList) {

                for (exercise in workout.exercises) {
                    val list = map[exercise.exerciseDescription]

                    if (list == null) {
                        exercise.exerciseDescription.id = 0 // вставляем 0 тчобы потом найти новые для записи в базу
                        exercise.id = 0
                        exercise.descriptionId = 0
                        map[exercise.exerciseDescription] = arrayListOf(exercise)

                    } else {
                        list.add(exercise)

                    }
                }

            }
        }

        val exerciseDescriptioDao = ExerciseDescriptionDao.getInstance(context, null)
        val exerciseDao = ExerciseDao.getInstance(context, null)
        for ((key, value) in map) {
            var idExDesc: Long
            if (key.id == 0L) {
                idExDesc = exerciseDescriptioDao.create(key)
                val templUserexercise = Exercise()
                templUserexercise.descriptionId = idExDesc
                templUserexercise.isDefaultType = false
                templUserexercise.isTemplate = true
                templUserexercise.typeMuscle = value[0].typeMuscle
                templUserexercise.exerciseDescription = key
                exerciseDao.create(templUserexercise)

                for (exer in value) {
                    exer.descriptionId = idExDesc
                }
            } else {

                for (exer in value) {
                    exer.descriptionId = key.id
                }
            }
        }

        val cycleDao = CycleDao.getInstance(context, null)
        val workoutDao = WorkoutDao.getInstance(context, null)
        val setDao = SetDao.getInstance(context, null)

        for (cycle in from) {
            cycle.id = 0
            val cycleId = cycleDao.create(cycle)

            for (workout in cycle.workoutList) {
                workout.id = 0
                workout.parentId = cycleId
                val workoutId = workoutDao.create(workout)

                for (exercise in workout.exercises) {
                    exercise.id = 0
                    exercise.parentId = workoutId
                    val exerciseId = exerciseDao.create(exercise)

                    for (sets in exercise.setsList) {
                        sets.id = 0
                        sets.parentId = exerciseId
                        setDao.create(sets)
                    }
                }

            }
        }

    }


    override fun writeToFile() {
        println("do write to file")

    }
}