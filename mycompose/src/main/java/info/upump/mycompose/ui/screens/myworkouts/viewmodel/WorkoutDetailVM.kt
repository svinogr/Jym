package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.ExerciseRepo
import info.upump.database.repo.WorkoutRepo
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class WorkoutDetailVM : BaseVMWithStateLoad() {
    private val _workout = MutableStateFlow(Workout(""))
    val workout = _workout.asStateFlow()

    private val _exercises = MutableStateFlow<List<Exercise>>(mutableListOf())
    val exercises = _exercises.asStateFlow()


    fun getWorkoutBy(id: Long) {
          _stateLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {

            val wFlow = WorkoutRepo.get().getBy(id).map {
                Workout.mapFromDbEntity(it)
            }

            val eflow = ExerciseRepo.get().getAllFullEntityByParent(id).map {
                it.map {
                    Exercise.mapFromFullDbEntity(it)
                }
            }

            wFlow.zip(eflow) { workout, exercise ->
                workout.exercises = exercise

                return@zip workout
            }.collect {
                Log.d("full", "$it")
                _workout.value = it
                _stateLoading.value = false
            }

        }

    }
}
