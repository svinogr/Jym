package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.CycleRepo
import info.upump.database.repo.WorkoutRepo
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.screenscomponents.WorkoutItemCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CycleDetailVM : BaseVMWithStateLoad() {
    private var _cycle = MutableStateFlow(Cycle(""))
    val cycle: StateFlow<Cycle> = _cycle.asStateFlow()

    private var _workout = MutableStateFlow<List<Workout>>(mutableListOf())
    val workout: StateFlow<List<Workout>> = _workout.asStateFlow()

    fun getDefaultCycleBy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true

            CycleRepo.get().getBy(id).map {
                Cycle.mapFromDbEntity(it)
            }.collect {
                WorkoutRepo.get().getAllByParent(id).map {
                    it.map { w -> Workout.mapFromDbEntity(w) }
                }.collect { list ->
                    _workout.value = list
                    _cycle.value = it
                    _stateLoading.value = false
                }
            }
        }
    }
}