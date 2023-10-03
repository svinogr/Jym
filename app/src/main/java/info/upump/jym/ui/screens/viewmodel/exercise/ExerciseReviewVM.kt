package info.upump.jym.ui.screens.viewmodel.exercise

import androidx.lifecycle.viewModelScope
import info.upump.database.repo.ExerciseRepo
import info.upump.jym.models.entity.Exercise
import info.upump.jym.models.entity.ExerciseDescription
import info.upump.jym.ui.screens.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ExerciseReviewVM : info.upump.mycompose.ui.screens.viewmodel.BaseVMWithStateLoad(), ExerciseReviewVMInterface {
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