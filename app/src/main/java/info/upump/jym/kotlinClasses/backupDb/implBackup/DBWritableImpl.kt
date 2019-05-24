package info.upump.jym.kotlinClasses.backupDb.implBackup

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.FileProvider
import info.upump.jym.R
import info.upump.jym.bd.*
import info.upump.jym.entity.Cycle
import info.upump.jym.entity.Exercise
import info.upump.jym.entity.ExerciseDescription
import info.upump.jym.kotlinClasses.backupDb.DBWritable
import java.io.File

class DBWritableImpl(val context: Context) : DBWritable {
    override fun writeToMail(): Intent {
        val intentToSendToBd = Intent(Intent.ACTION_SEND)
        val uri = Uri.parse(DBHelper.DB_PATH)
        println(uri)

        val file = File(context.filesDir, "database/jym.db")
        println("dedededed ${file.exists()}")

        val ur = FileProvider.getUriForFile(context, "info.upump.jym", file)
        //val ur = Uri.parse("file://"+file.absolutePath)
        println(ur)
        intentToSendToBd.type = "text/plain"
        intentToSendToBd.putExtra(Intent.EXTRA_STREAM, ur)
        //   intentToSendToBd.putExtra(Intent.EXTRA_STREAM, uri)
//intentToSendToBd.data = ur
        // intentToSendToBd.flags= Intent.FLAG_ACTIVITY_NEW_TASK
        intentToSendToBd.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intentToSendToBd.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intentToSendToBd.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject_for_beackup))
        return intentToSendToBd
    }

    override fun writeToDB(from: List<Cycle>) {
        if (from.isEmpty()) return

        val map = hashMapOf<ExerciseDescription, MutableList<Exercise>>()

        inflateMapWithExerciceDescriptionFromCurrentDb(map)

        inflateMapWithExerciseDescriptionFromBackupDB(from, map)

        createTempleExerciseFromBackupDb(map)

        write(from)

    }

    private fun write(from: List<Cycle>) {
        val cycleDao = CycleDao.getInstance(context, null)
        val workoutDao = WorkoutDao.getInstance(context, null)
        val exerciseDao = ExerciseDao.getInstance(context, null)
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

    private fun createTempleExerciseFromBackupDb(map: HashMap<ExerciseDescription, MutableList<Exercise>>) {
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
    }

    private fun inflateMapWithExerciseDescriptionFromBackupDB(from: List<Cycle>, map: HashMap<ExerciseDescription, MutableList<Exercise>>) {
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
    }

    private fun inflateMapWithExerciceDescriptionFromCurrentDb(map: HashMap<ExerciseDescription, MutableList<Exercise>>) {
        val exerciseDaoBackup = ExerciseDescriptionDao.getInstance(context, null)
        val oldExerciseDescriptionList = exerciseDaoBackup.all

        if (oldExerciseDescriptionList != null) {
            for (exersciseDescription in oldExerciseDescriptionList) {
                val list = map[exersciseDescription]

                if (list == null) {
                    map[exersciseDescription] = arrayListOf()
                }
            }
        }


    }


    override fun writeToFile(): Intent {

        val intentToSend = Intent(Intent.ACTION_SEND)

        return Intent()
        println("do write to file")

    }
}