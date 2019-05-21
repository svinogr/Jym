package info.upump.jym.kotlinClasses.backupDb.implBackup

import android.content.Context
import info.upump.jym.bd.ExerciseDescriptionDao
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

        val exerciseDao = ExerciseDescriptionDao.getInstance(context, null)
        val oldExerciseDescriptionList = exerciseDao.all
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
                        map[exercise.exerciseDescription] = arrayListOf(exercise)

                    } else {
                        list.add(exercise)

                    }
                }

            }
        }


        for ((key, value) in map) {

            if (value.size > 0) {
                println("map size $value")
            }
        }


        println("do write to DB")
    }

    override fun writeToFile() {
        println("do write to file")

    }
}