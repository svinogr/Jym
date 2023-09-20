package info.upump.mycompose.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import info.upump.database.DatabaseApp
import info.upump.database.RoomDB
import info.upump.database.entities.CycleFullEntity
import info.upump.database.entities.CycleFullEntityWithWorkouts
import info.upump.database.repo.CycleRepo
import info.upump.mycompose.R
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class DBRestoreBackup() {
    fun getSendIntent(context: Context): Intent {
        val intentToSendToBd = Intent(Intent.ACTION_SEND)
        val ur = DBProvider().getDatabaseURI(context)
        intentToSendToBd.type = "text/plain"
        intentToSendToBd.putExtra(Intent.EXTRA_STREAM, ur)
        intentToSendToBd.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intentToSendToBd.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intentToSendToBd.putExtra(
            Intent.EXTRA_SUBJECT,
            context.getString(R.string.email_subject_for_beackup)
        )

        return intentToSendToBd
    }

    suspend fun restore(uri: Uri, context: Context) {
        coroutineScope {
            launch {
                val bytes = context.contentResolver.openInputStream(uri)?.use {
                    it.readBytes()
                }

                val file = File(RoomDB.DB_PATH_RESTORE, RoomDB.BASE_NAME_RESTORE)

                withContext(Dispatchers.IO) {
                    FileOutputStream(file)
                }?.use {
                    it.write(bytes)
                    DatabaseApp.initilizeDb(
                        context,
                        RoomDB.BASE_NAME_RESTORE,
                        RoomDB.DB_PATH_RESTORE,
                        true
                    )

                    val repo = CycleRepo.get() as CycleRepo
                    val list = repo.getAllFullestEntityPersonal()
                    list.onEach {
                        Log.d("jhj", it.size.toString())
                        prepareForWriteInDb(it)

                    }.collect() {
                        println("-------- -------- --------")
                        DatabaseApp.initilizeDb(context, RoomDB.BASE_NAME, RoomDB.DB_PATH)
                    }
                }
            }
        }
    }

    private suspend fun prepareForWriteInDb(list: List<CycleFullEntity>): List<CycleFullEntity> {
        val listForDbWrite = mutableListOf<Deferred<CycleFullEntity>>()
        coroutineScope {

            for (cycle in list) {
                Log.d("prepareForWriteInDb", "-cycle")

                async {
                    cycle.cycleEntity._id = 0

                    for (workout in cycle.listWorkoutEntity) {
                        Log.d("prepareForWriteInDb", "--workout")

                        async {
                            workout.workoutEntity._id = 0

                            for (exercise in workout.listExerciseEntity) {
                                Log.d("prepareForWriteInDb", "---esercise")

                                async {

                                    exercise.exerciseEntity._id = 0

                                    for(set in exercise.listSetsEntity){
                                      Log.d("prepareForWriteInDb", "----set")
                                        async {
                                            set._id = 0
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }

        return listForDbWrite.awaitAll()
    }
}