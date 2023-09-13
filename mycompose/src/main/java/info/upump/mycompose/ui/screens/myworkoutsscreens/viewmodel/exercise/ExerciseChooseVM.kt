package info.upump.mycompose.ui.screens.myworkoutsscreens.viewmodel.exercise

import androidx.lifecycle.viewModelScope
import info.upump.database.repo.ExerciseRepo
import info.upump.database.repo.WorkoutRepo
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.TypeMuscle
import info.upump.mycompose.ui.screens.myworkoutsscreens.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ExerciseChooseVM : BaseVMWithStateLoad(), ExerciseChooseVMInterface {
    private val exerciseRepo = ExerciseRepo.get()
    private val workoutRepo = WorkoutRepo.get()
    private var parentId: Long = 0L

    private val _day = MutableStateFlow(Day.TUESDAY) //TODO изменить на каритинку
    override val day: StateFlow<Day> = _day

    private val _subItems = MutableStateFlow<List<Exercise>>(mutableListOf())
    override val subItems: StateFlow<List<Exercise>> = _subItems

    override fun getAllExerciseForParent(parentId: Long) {
        this.parentId = parentId

        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true

            exerciseRepo.getAllFullEntityTemplate().map {
                it.map { e ->
                    Exercise.mapFromFullDbEntity(e)
                }
            }.collect { list ->
                _subItems.value = list
            }
        }
    }

    override fun saveForParentChosen(it: Long) {
    }

    fun filterBy(type: TypeMuscle) {
        _subItems.value.filter {
            it.typeMuscle == type
        }
    }


}