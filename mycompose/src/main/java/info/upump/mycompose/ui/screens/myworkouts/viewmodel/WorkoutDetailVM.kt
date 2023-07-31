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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WorkoutDetailVM : BaseVMWithStateLoad() {
    private val _workout = MutableStateFlow(Workout(""))
    val workout = _workout.asStateFlow()

    fun getWorkoutBy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val workoutRepo = WorkoutRepo.get().getBy(id).map {
                Workout.mapFromDbEntity(it)
            }.collect{
                _workout.value = it
            }

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

}
