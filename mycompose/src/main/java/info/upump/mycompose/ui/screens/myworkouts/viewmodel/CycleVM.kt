package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.os.StatFs
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.CycleRepo
import info.upump.mycompose.models.entity.Cycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CycleVM : BaseVMWithStateLoad() {
    private val _cycles = MutableStateFlow<List<Cycle>>(listOf())
    val cycles: StateFlow<List<Cycle>> = _cycles.asStateFlow()

    private val _cycle = MutableStateFlow(Cycle())
    val cycle: StateFlow<Cycle> = _cycle.asStateFlow()
    fun getAllPersonal() {
        _stateLoading.value = true

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val list = CycleRepo.get().getAllPersonal()
            list.map {
                it.map {
                    Cycle.mapFromDbEntity(it)
                }
            }.collect {
                _cycles.value = it
            }
            _stateLoading.value = false
        }
    }

    fun getCycle(id: Long) {
       viewModelScope.launch(Dispatchers.IO) {
           if (id == 0L) {
               _cycle.value = Cycle().apply { title = "Новая программа" }
               Log.d("getCycle", "${cycle.value}")
               return@launch
           }

           CycleRepo.get().getBy(id).map {
               Cycle.mapFromDbEntity(it)
           }.collect{
             _cycle.value = it
           }
       }
    }

    fun saveCycle() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("save", "${cycle.value}")
            CycleRepo.get().save(Cycle.mapToEntity(cycle.value))
        }
    }

}