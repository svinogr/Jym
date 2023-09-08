package info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.entities.CycleEntity
import info.upump.database.repo.CycleRepo
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// для списка всех cycles
class CycleVM : BaseVMWithStateLoad() {
    private val _cycleList = MutableStateFlow<List<Cycle>>(listOf())
    val cycleList: StateFlow<List<Cycle>> = _cycleList.asStateFlow()
    val cycleRepo = CycleRepo.get()

   suspend fun getAllPersonal() {
        Log.d("getAllPersonal", "getAllPersonal")
        _stateLoading.value = true

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val list = cycleRepo.getAllPersonal()
            list.map {
                it.map { c ->
                    Cycle.mapFromDbEntity(c)
                }
            }.collect {
                _cycleList.value = it
            }

            _stateLoading.value = false
        }
    }

   fun delete(id: Long) {
          viewModelScope.launch(Dispatchers.IO) {
              cycleRepo.deleteBy(id)
          }
    }

    fun exp(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            (cycleRepo as CycleRepo).exp(id).collect{
                Log.d("fullll", "${it.cycle._id}")
                Log.d("fullll", "_____________________________________")

                Log.d("fullll", "${it.listWorkouts[0].parent_id}")
                Log.d("fullll", "_____________________________________")
               Log.d("fullll", "${it.listExercise[0].parent_id}")
           //     Log.d("fullll", "${it.listSet[0].listSet[0].listSet[0].toString()}")


            }
        }
    }


}