package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.CycleRepo
import info.upump.database.repo.WorkoutRepo
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CycleDetailVM : BaseVMWithStateLoad() {
    private var _cycle = MutableStateFlow(Cycle(""))
    val cycle: StateFlow<Cycle> = _cycle.asStateFlow()
    fun getDefaultCycleBy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true
            val fCycle = CycleRepo.get().getBy(id).map {
               Cycle.mapFromDbEntity(it)
            }
            fCycle.collect{
                _cycle.value = it
            }

            }

        /*    val map = fCycle.map {
                val mapFromDbEntity = Cycle.mapFromDbEntity(it)

                WorkoutRepo.get().getAllByParent(id).map { list ->
                    val list = list.map { entity -> Workout.mapFromDbEntity(entity) }
                    mapFromDbEntity.workoutList = list
                }

            }*/

            /*   _stateLoading.value = true
               val getCycle = Cycle.mapFromDbEntity(CycleRepo.get().getBy(id))
               val listWorkouts = WorkoutRepo.get().getAllByParent(id)
               getCycle.workoutList = listWorkouts.map { Workout.mapFromDbEntity(it) }
               Log.d("TAG", "таг        $getCycle")
               _cycle.value = getCycle
               _stateLoading.value = false*/
        //}
    }
}