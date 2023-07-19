//package info.upump.jym.kotlinClasses.backupDb.implBackup
//
//import android.content.Context
//import android.net.Uri
//import info.upump.jym.bd.CycleDao
//import info.upump.jym.bd.DBHelperForBackup
//import info.upump.jym.bd.WorkoutDao
//import info.upump.jym.entity.Cycle
//import info.upump.jym.kotlinClasses.backupDb.DBReadable
//import java.io.*
//
//class DBReadableImpl(val context: Context) : DBReadable {
//
//    override fun readFrom(fromUri: Uri): List<Cycle> {
//        val copyFileDb = copyFileDb(fromUri)
//        var list: MutableList<Cycle> = ArrayList()
//
//        if (copyFileDb) {
//            val cycleDao = CycleDao.getInstance(context, fromUri)
//            list = cycleDao.allUserInflated
//
//            val workoutDao = WorkoutDao.getInstance(context, fromUri)
//            val listWorkout = workoutDao.getAllUserInflated(0)
//            if (listWorkout.size > 0) {
//                for (workout in listWorkout) {
//                    val cycle = Cycle()
//                    cycle.workoutList.add(workout)
//                    cycle.isDefaultType = true //спорный момент определения что это пользовательская программа чтобы ее потом не записать в базу а только тренировки из нее
//                    list.add(cycle)
//                }
//            }
//
//        }
//        return list
//
//    }
//
//    private fun copyFileDb(fromUri: Uri): Boolean {
//        var write = false
//        var myInput: InputStream? = null
//        var myOutput: OutputStream? = null
//
//        //получаем директорию приложения куда будем копировать временную базу
//        val outFileName = DBHelperForBackup.DB_PATH_FOR_BACKUP + DBHelperForBackup.DATABASE_NAME
//        val fileOutput = File(outFileName)
//        try {
//            //получаем файл через ContextResolver!!!
//            myInput = context.contentResolver.openInputStream(fromUri)
//            myOutput = FileOutputStream(fileOutput)
//            // побайтово копируем данные
//            val buffer = ByteArray(1024)
//            var length: Int
//
//            do {
//                length = myInput!!.read(buffer)
//                if (length < 1) break
//                myOutput.write(buffer, 0, length)
//            } while (length > 0)
//
//            write = true
//
//        } catch (e1: FileNotFoundException) {
//            e1.printStackTrace()
//        } catch (e1: IOException) {
//            e1.printStackTrace()
//        } finally {
//            myOutput?.flush()
//            myOutput?.close()
//            myInput?.close()
//        }
//        return write
//
//    }
//}