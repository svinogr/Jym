package info.upump.jym.kotlinClasses.backupDb.implBackup

import android.content.Context
import android.net.Uri
import info.upump.jym.bd.CycleDao
import info.upump.jym.bd.DBHelper
import info.upump.jym.bd.ExerciseDao
import info.upump.jym.entity.Cycle
import info.upump.jym.entity.Exercise
import info.upump.jym.entity.ExerciseDescription
import info.upump.jym.kotlinClasses.backupDb.DBReadable
import java.io.File
import java.util.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.set

class DBReadableImpl(val context: Context) : DBReadable {
    //чтение из родной базы

/*

    File file = new File(DB_PATH);
    if (!file.exists()) {
        //получаем локальную бд как поток в папке assets
        this.getReadableDatabase();
        myInput = context.getAssets().open(DATABASE_NAME);
        // Путь к новой бд
        String outFileName = DB_PATH;
        // Открываем пустую бд
        myOutput = new FileOutputStream(outFileName);
        // побайтово копируем данные
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
*/


    override fun readFrom() {
        val file = File(DBHelper.DB_PATH)
        if (!file.exists()) {
            println("File DB  wasnt createde ")
            return
        }

        println("${file.totalSpace} ${file.absolutePath} ${file.totalSpace}")


    }

    override fun readFrom(fromUri: Uri): Pair<List<Cycle>, Map<ExerciseDescription, List<Exercise>>>? {

        ///проверить на наличе файла
        val file = File(fromUri.path)
        print("абсол $file.absolutePath")

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
}