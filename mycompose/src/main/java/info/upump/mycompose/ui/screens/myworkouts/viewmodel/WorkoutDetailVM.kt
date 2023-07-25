package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.ExerciseDescriptionRepo
import info.upump.database.repo.ExerciseRepo
import info.upump.database.repo.WorkoutRepo
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.ExerciseDescription
import info.upump.mycompose.models.entity.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkoutDetailVM : BaseVMWithStateLoad() {
    private val _workout = MutableStateFlow(Workout(""))
    val workout = _workout.asStateFlow()

    fun getWorkoutBy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true
            val workootRepo = WorkoutRepo.get()
            val workout = Workout.mapFromDbEntity(workootRepo.getBy(id))
            val exercRepo = ExerciseRepo.get()
            Log.d("workout", "$id")
            Log.d("workout", "1 $workout")
            val getExercises = exercRepo.getAllByParent(id)
            val exercDescrptRepo = ExerciseDescriptionRepo.get()

            val exercises = getExercises
                .map { Exercise.mapFromDbEntity(it) }
            exercises.forEach { it ->
                val description: ExerciseDescription =
                    ExerciseDescription.mapFromDbEntity(exercDescrptRepo.getBy(it.descriptionId))
                it.exerciseDescription = description
            }

             workout.exercises = exercises
            Log.d("workout", "2 $workout")
            _workout.value = workout
            _stateLoading.value = false
        }
    }

}
