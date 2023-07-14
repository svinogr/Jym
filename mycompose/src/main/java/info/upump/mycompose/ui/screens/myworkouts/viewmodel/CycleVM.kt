package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.os.StatFs
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
import kotlinx.coroutines.launch

class CycleVM : BaseVMWithStateLoad() {
    private val _cycles = MutableStateFlow<List<Cycle>>(listOf())
    val cycles: StateFlow<List<Cycle>> = _cycles.asStateFlow()

    fun getAllDefaultCycles() {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            _stateLoading.value = true
            val list = CycleRepo.get().getAllDefault()
            _cycles.value = list.map {Cycle.mapFromDbEntity(it) }
            _stateLoading.value = false
        }
    }
}