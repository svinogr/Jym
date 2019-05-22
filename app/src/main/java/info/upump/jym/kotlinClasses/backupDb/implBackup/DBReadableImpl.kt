package info.upump.jym.kotlinClasses.backupDb.implBackup

import android.content.Context
import android.net.Uri
import info.upump.jym.bd.CycleDao
import info.upump.jym.bd.DBHelper
import info.upump.jym.bd.DBHelperForBackup
import info.upump.jym.entity.Cycle
import info.upump.jym.kotlinClasses.backupDb.DBReadable
import java.io.*

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

    override fun readFrom(fromUri: Uri): List<Cycle> {
        val copyFileDb = copyFileDb(fromUri)
        var list: List<Cycle> = ArrayList<Cycle>()

        if (copyFileDb) {

            val cycleDao = CycleDao.getInstance(context, fromUri)
            list = cycleDao.allUserInflated
        }
        return list

//        val mapOfExerciseDescriptionToListExercise = HashMap<ExerciseDescription, List<Exercise>>()
//
//        val exerciseDao = ExerciseDao.getInstance(context, fromUri)
//        val exerciseUserOnlyTemplateList = exerciseDao.allUserTemplateExercises
//
//        //добавляем в мапу ключем описание упражнения(ex desc)
//        // и пустой лист для упражнени(пустой так как это темплайты описаний)
//        for (exercise in exerciseUserOnlyTemplateList) {
//            mapOfExerciseDescriptionToListExercise[exercise.exerciseDescription] = ArrayList()
//        }
//
//        print("razmer userList template  exercise ${exerciseUserOnlyTemplateList.size}")
//
//        // получаем пользовательские программы
//        val cycleDao = CycleDao.getInstance(context, fromUri)
//        val userCycleList = cycleDao.allUserInflated
//
//        for (cycle in userCycleList) {
//            //получаем лист всех тренировок
//            val exercisesList = (cycle.workoutList).flatMap { it.exercises }
//
//            print("razmer listExDesc ${exercisesList.size}")
//
//            //добавляем в мапу по ключу описание тренировки саму тренировку
//            for (exercise in exercisesList) {
//                val listExerciseForDescription = mapOfExerciseDescriptionToListExercise[exercise.exerciseDescription]
//
//                if (listExerciseForDescription.isNullOrEmpty()) {
//                    mapOfExerciseDescriptionToListExercise[exercise.exerciseDescription] = listOf(exercise)
//                } else listExerciseForDescription.plus(exercise)
//            }
//
//        }
//
//        println("razmer map ${mapOfExerciseDescriptionToListExercise.size}")
//
//        return Pair(userCycleList, mapOfExerciseDescriptionToListExercise)

    }

    private fun copyFileDb(fromUri: Uri): Boolean {
        var write = false
        var myInput: InputStream? = null
        var myOutput: OutputStream? = null

        //получаем директорию приложения куда будем копировать временную базу
        val outFileName = DBHelperForBackup.DB_PATH_FOR_BACKUP + DBHelperForBackup.DATABASE_NAME
        val fileOutput = File(outFileName)
        try {
            //получаем файл через ContextResolver!!!
            myInput = context.contentResolver.openInputStream(fromUri)
            myOutput = FileOutputStream(fileOutput)
            // побайтово копируем данные
            val buffer = ByteArray(1024)
            var length: Int

            do {
                length = myInput.read(buffer)
                if (length < 1) break
                myOutput.write(buffer, 0, length)
            } while (length > 0)

            /*  while ((length = myInput.read(buffer)) > 0) {
                  myOutput!!.write(buffer, 0, length)
              }*/

            myOutput.flush()
            myOutput.close()
            myInput!!.close()
            write = true

        } catch (e1: FileNotFoundException) {
            e1.printStackTrace()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return write

    }
}