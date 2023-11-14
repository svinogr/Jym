package info.upump.jym.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.reflect.TypeToken
import info.upump.database.RoomDB
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntity
import info.upump.database.repo.CycleRepo
import info.upump.jym.R
import info.upump.jym.models.entity.Cycle
import info.upump.jym.models.entity.Exercise
import info.upump.jym.models.entity.Sets
import info.upump.jym.models.entity.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import okio.use
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class JSONRestoreBackup : RestoreBackupable {
    override suspend fun getSendIntent(context: Context): Intent =
            withContext(Dispatchers.IO) {

                val cycleRepo = CycleRepo.get() as CycleRepo
                val listEntities = cycleRepo.getAllFullestEntityPersonal()
                val listToJson = mutableListOf<Cycle>()

                for (cE in listEntities) {
                    val cycle = Cycle.mapFromDbEntity(cE.cycleEntity)
                    val workoutList = mutableListOf<Workout>()
                    cycle.workoutList = workoutList

                    for (wE in cE.listWorkoutEntity) {
                        val workout = Workout.mapFromDbEntity(wE.workoutEntity)
                        workoutList.add(workout)

                        val exerciseList = mutableListOf<Exercise>()
                        workout.exercises = exerciseList

                        for (eE in wE.listExerciseEntity) {
                            val exercise = Exercise.mapFromDbEntity(eE.exerciseEntity)
                            exerciseList.add(exercise)

                            val setList = mutableListOf<Sets>()
                            exercise.setsList = setList

                            for (sE in eE.listSetsEntity) {
                                 val sets = Sets.mapFromDbEntity(sE)
                                setList.add(sets)
                            }
                        }
                    }

                    listToJson.add(cycle)
                }

                val intentToSendToBd = Intent(Intent.ACTION_SEND)

                val gson = Gson()

                val toJson = gson.toJson(listToJson)

                val file = File(RoomDB.DB_PATH_RESTORE, RoomDB.BASE_NAME_RESTORE)
                FileOutputStream(file)
                        .use {
                            it.write(toJson.toByteArray())
                        }

                val uri = DBProvider().getDatabaseURIForJsonFile(context, file)

                intentToSendToBd.type = "text/plain"
                intentToSendToBd.putExtra(Intent.EXTRA_STREAM, uri)
                intentToSendToBd.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intentToSendToBd.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                intentToSendToBd.putExtra(
                        Intent.EXTRA_SUBJECT,
                        context.getString(R.string.email_subject_for_beackup)
                )

                return@withContext intentToSendToBd
            }


    override suspend fun restore(uri: Uri, context: Context, _stateLoading: MutableStateFlow<Boolean>) {
        withContext(Dispatchers.IO) {
            var fromJson = listOf<Cycle>()

            context.contentResolver.openInputStream(uri)?.use { inS ->
                inS.bufferedReader().use {
                    val readText = it.readText()
                    Log.d("restore", readText)
                    val type = object : TypeToken<List<Cycle>>() {}.type
                    fromJson = Gson().fromJson(readText, type)
                    Log.d("restore", fromJson.toString())

                }


                //    val file = File("")
                //  val bA = inS.readBytes()

                /* FileOutputStream(file).use {
                 it.write(bA)
             }
 */

                //   val fromJson = Gson().fromJson(file.readText(), CycleFullEntity::class.java)

                //  Log.d("restore", fromJson.cycleEntity.title)
            }

            for (cE in fromJson) {
                val cycleE = Cycle.mapToEntity(cE)

                val workoutList = mutableListOf<Workout>()
                cycle.workoutList = workoutList

                for (wE in cE.listWorkoutEntity) {
                    val workout = Workout.mapFromDbEntity(wE.workoutEntity)
                    workoutList.add(workout)

                    val exerciseList = mutableListOf<Exercise>()
                    workout.exercises = exerciseList

                    for (eE in wE.listExerciseEntity) {
                        val exercise = Exercise.mapFromDbEntity(eE.exerciseEntity)
                        exerciseList.add(exercise)

                        val setList = mutableListOf<Sets>()
                        exercise.setsList = setList

                        for (sE in eE.listSetsEntity) {
                            val sets = Sets.mapFromDbEntity(sE)
                            setList.add(sets)
                        }
                    }
                }

                listToJson.add(cycle)
            }

            val cycleRepo = CycleRepo.get() as CycleRepo
            cycleRepo.saveFullEntitiesOnlyFromOtherDB()

        }

    }
}