package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.ExerciseDescriptionRepo
import info.upump.database.repo.ExerciseRepo
import info.upump.database.repo.SetsRepo
import info.upump.database.repo.WorkoutRepo
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.ExerciseDescription
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.models.entity.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class WorkoutDetailVM : BaseVMWithStateLoad() {
    private val _workout = MutableStateFlow(Workout(""))
    val workout = _workout.asStateFlow()

    private val _exercises = MutableStateFlow<List<Exercise>>(mutableListOf())
    val exercises = _exercises.asStateFlow()


    fun getWorkoutBy(id: Long) {
        //  _stateLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val w = WorkoutRepo.get().getBy(id).map {
                Workout.mapFromDbEntity(it)
            }

            val e = ExerciseRepo.get().test(id).map {
              //  val map = mutableMapOf<Exercise, ExerciseDescription>()
                val listExercise = mutableListOf<Exercise>()
                it.forEach { entry ->
                    val e = Exercise.mapFromDbEntity(entry.key)
                    val d = ExerciseDescription.mapFromDbEntity(entry.value)
                    Log.d("forEach", "$e")
                    Log.d("forEach", "$d")
                    e.exerciseDescription = d
                    Log.d("forEach", "$e")
                    listExercise.add(e)
                }
                return@map listExercise
            }

            w.zip(e) { workou, exercise ->
                workou.exercises = exercise
                return@zip workou
            }.collect{
                _workout.value = it
                _exercises.value = it.exercises
            }


            /*      w.zip(e) { workoutM, exercises ->
                      workoutM.exercises = exercises
                      return@zip workoutM
                  }.collect {
                      Log.d("desc 6", "2 ${it.exercises[0].exerciseDescription}")
                      Log.d("desc 6", "2 ${it.exercises[0].setsList.size}")
                      _workout.value = it
                      _exercises.value = it.exercises
                      _stateLoading.value = false
                  }
      */

            /*w.zip(e) { workout, exercises ->
                val l = mutableListOf<Exercise>()
                exercises.forEach { exercise ->
                     ExerciseDescriptionRepo.get().getBy(exercise.descriptionId).map {
                        val e = ExerciseDescription.mapFromDbEntity(it)
                        Log.d("desc", "1 $e")
                       return@map e
                    }.collect{it ->
                         exercise.exerciseDescription = it}

                    SetsRepo.get().getAllByParent(exercise.id).map{
                     val p = it.map{
                         Log.d("desc", "1 $e")
                           Sets.mapFromDbEntity(it)

                        }
                        return@map p
                    }.collect{
                        exercise.setsList = it
                    }

                    l.add(exercise)

                }

                workout.exercises = l
                return@zip workout

            }.collect{
                Log.d("desc", "2 ${it.exercises.size}")
                _workout.value = it
                _stateLoading.value = false
            }
*/

            /*      w.zip(e) { wo, ex ->
                      wo.exercises = ex
                      return@zip wo

                  }.collect { it ->
                      _workout.value = it
                      _stateLoading.value = false
                  }*/

        }


        /*         SetsRepo.get().getAllByParent(ex.id).collect{listSet ->
                     val j =  listSet.map{entity ->
                         Sets.mapFromDbEntity(entity)
                     }

                     ex.setsList = j

                 }
     */
        /*      viewModelScope.launch(Dispatchers.IO) {
            WorkoutRepo.get().getBy(id).map {
                Workout.mapFromDbEntity(it)
            }.collect { workout ->
                ExerciseRepo.get().getAllByParent(id).map { list ->
                    list.map { entity ->
                        Exercise.mapFromDbEntity(entity)

                    }.map { exercise ->
                        ExerciseDescriptionRepo.get().getBy(exercise.descriptionId).collect {
                            exercise.exerciseDescription =
                                ExerciseDescription.mapFromDbEntity(it)


                        }
                    }

                }


                    .collect {


                        _exercises.value = it
                        _workout.value = workout
                        _stateLoading.value = false
                    }

            }*/

        //было до
        /*        _stateLoading.value = true
                    val workoutRepo = WorkoutRepo.get()
                    val workout = Workout.mapFromDbEntity(workoutRepo.getBy(id))

                    val exerciseRepo = ExerciseRepo.get()
                    val exercisesEntitiesList = exerciseRepo.getAllByParent(id)

                    val exerciseDescRepo = ExerciseDescriptionRepo.get()

                    val setsRepo = SetsRepo.get()

                    val exercises = exercisesEntitiesList
                        .map { Exercise.mapFromDbEntity(it) }

                    exercises.forEach { it ->
                        val description = ExerciseDescription.mapFromDbEntity(exerciseDescRepo.getBy(it.descriptionId))
                        val listSets = setsRepo.getAllByParent(it.id).map { Sets.mapFromDbEntity(it) }

                        it.exerciseDescription = description
                        it.setsList = listSets
                    }

                    Log.d("desc", exercises.toString())


                    workout.exercises = exercises
                    Log.d("workout", "2 $workout")
                    _workout.value = workout
                    _stateLoading.value = false*/
    }
}
/* }

}*/
