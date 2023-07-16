package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.CycleRepo
import info.upump.database.repo.WorkoutRepo
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CycleDetailVM : BaseVMWithStateLoad() {
    private val _cycle = MutableStateFlow(Cycle(title = ""))
    val cycle: StateFlow<Cycle> = _cycle.asStateFlow()

    fun getDefaultCycleBy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true
            val getCycle = Cycle.mapFromDbEntity(CycleRepo.get().getBy(id))
            val listWorkouts = WorkoutRepo.get().getAllByParent(id)
            getCycle.workoutList = listWorkouts.map { Workout.mapFromDbEntity(it) }
            Log.d("TAG", "таг        $getCycle")
            _cycle.value = getCycle
            _stateLoading.value = false
        }
    }
}