package info.upump.jym.kotlinClasses.backupDb.implBackup

import android.content.Context
import info.upump.jym.bd.CycleDao
import info.upump.jym.bd.ExerciseDao
import info.upump.jym.entity.Cycle
import info.upump.jym.entity.Exercise
import info.upump.jym.entity.ExerciseDescription
import info.upump.jym.kotlinClasses.backupDb.DBReadable
import java.util.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.flatMap
import kotlin.collections.isNullOrEmpty
import kotlin.collections.listOf
import kotlin.collections.plus
import kotlin.collections.set

class DBReadableImpl(val context: Context) : DBReadable {
    //чтение из родной базы
    override fun readFrom(): Pair<List<Cycle>, Map<ExerciseDescription, List<Exercise>>>? {
        val mapOfExerciseDescriptionToListExercise = HashMap<ExerciseDescription, List<Exercise>>()

        val exerciseDao = ExerciseDao(context)
        val exerciseUserOnlyTemplateList = exerciseDao.allUserTemplateExercises

        //добавляем в мапу ключем описание упражнения(ex desc)
        // и пустой лист для упражнени(пустой так как это темплайты описаний)
        for (exercise in exerciseUserOnlyTemplateList) {
            mapOfExerciseDescriptionToListExercise[exercise.exerciseDescription] = ArrayList()
        }

        print("razmer userList template  exercise ${exerciseUserOnlyTemplateList.size}")

        // получаем пользовательские программы
        val cycleDao = CycleDao(context)
        val userCycleList = cycleDao.allUserInflated

        for (cycle in userCycleList) {
            //получаем лист всех тренировок
            val exercisesList = (cycle.workoutList).flatMap { it.exercises }

            print("razmer listExDesc ${exercisesList.size}")

            //добавляем в мапу по ключу описание тренировки саму тренировку
            for (exercise in exercisesList) {
                val listExerciseForDescription = mapOfExerciseDescriptionToListExercise[exercise.exerciseDescription]

                if (listExerciseForDescription.isNullOrEmpty()) {
                    mapOfExerciseDescriptionToListExercise[exercise.exerciseDescription] = listOf(exercise)
                } else listExerciseForDescription.plus(exercise)
            }

        }

        println("razmer map ${mapOfExerciseDescriptionToListExercise.size}")

        return Pair(userCycleList, mapOfExerciseDescriptionToListExercise)
    }

    override fun readFromFile(fileName: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}