package info.upump.mycompose.ui.screens.myworkouts.viewmodel.exercise

import androidx.lifecycle.viewModelScope
import info.upump.database.repo.ExerciseRepo
import info.upump.database.repo.WorkoutRepo
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class ExerciseChooseVM : BaseVMWithStateLoad(), ExerciseChooseVMInterface {
    private val exerciseRepo = ExerciseRepo.get()
    private val workoutRepo = WorkoutRepo.get()


    private val _day = MutableStateFlow(Day.TUESDAY)
    override val day: StateFlow<Day> = _day

    private val _subItems = MutableStateFlow<List<Exercise>>(mutableListOf())
    override val subItems: StateFlow<List<Exercise>> = _subItems

    override fun getByParent(parentId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true
            val w = workoutRepo.getBy(parentId)
            val e = exerciseRepo.getAllFullEntity()

            w.zip(e) { workout, listExercise ->

                val w = Workout.mapFromDbEntity(workout)
                val list = mutableListOf<Exercise>()
                listExercise.onEach() {
                    list.add(Exercise.mapFromFullDbEntity(it))
                }

                w.exercises = list
                return@zip w
            }.collect() { wor ->
                _day.value = wor.day
                _subItems.value = wor.exercises
            }
        }
    }
}