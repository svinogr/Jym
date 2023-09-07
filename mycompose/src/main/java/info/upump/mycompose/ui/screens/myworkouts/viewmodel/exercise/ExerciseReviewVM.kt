package info.upump.mycompose.ui.screens.myworkouts.viewmodel.exercise

import androidx.lifecycle.viewModelScope
import info.upump.database.repo.ExerciseRepo
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.ExerciseDescription
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ExerciseReviewVM : BaseVMWithStateLoad(), ExerciseReviewVMInterface {
    private val exerciseRepo = ExerciseRepo.get()

    private val _item = MutableStateFlow(Exercise().apply {
        exerciseDescription = ExerciseDescription()
    })
    override val item: StateFlow<Exercise> = _item
    override fun getItem(id: Long) {
        _stateLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            exerciseRepo.getFullEntityBy(id).map {
                Exercise.mapFromFullDbEntity(it)
            }.collect() { ex ->
                _item.value = ex
            }
        }
    }
}