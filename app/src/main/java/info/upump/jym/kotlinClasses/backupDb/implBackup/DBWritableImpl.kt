package info.upump.jym.kotlinClasses.backupDb.implBackup

import android.content.Context
import android.net.Uri
import info.upump.jym.bd.*
import info.upump.jym.entity.Cycle
import info.upump.jym.entity.Exercise
import info.upump.jym.entity.ExerciseDescription
import info.upump.jym.kotlinClasses.backupDb.DBWritable
import info.upump.jym.utils.fileprovider.MyFileProvider
import java.io.*
private const val NAME_FOR_BACKUP_DB = "backupUpump.db"
class DBWritableImpl(val context: Context) : DBWritable {
    override fun writeToFile(to: Uri) {
        val fileTo = File(to.path, NAME_FOR_BACKUP_DB)
        val provider = MyFileProvider()
        val ura = provider.getDatabaseURI(context)
        var inputStream: InputStream? = null
        var myOutput: OutputStream? = null

        try {

            inputStream = context.contentResolver.openInputStream(ura)
            // inputStream = context.contentResolver.openInputStream(Uri.parse("/data/data/info.upump.jym/database/jym.db"))
            println(inputStream == null)
            myOutput = FileOutputStream(fileTo)
            // побайтово копируем данные
            val buffer = ByteArray(1024)
            var length: Int

            do {
                length = inputStream!!.read(buffer)
                if (length < 1) break
                myOutput.write(buffer, 0, length)
            } while (length > 0)

        } catch (e1: FileNotFoundException) {
            e1.printStackTrace()
        } catch (e1: IOException) {
            e1.printStackTrace()
        } finally {
            myOutput?.flush()
            myOutput?.close()
            inputStream?.close()
        }
    }

    override fun writeToMail() {
        // NOP
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

            var cycleId = 0L // назначем ноль чтобы не было записи в базу Cycle
            if (!cycle.isDefaultType) {
                cycleId = cycleDao.create(cycle)
            }

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


}