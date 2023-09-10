package info.upump.mycompose.ui.screens.defaultscreen.viewmodel

import android.os.StatFs
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.CycleRepo
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CycleDefaultVM : BaseVMWithStateLoad() {
    private val _cycles = MutableStateFlow<List<Cycle>>(listOf())
    val cycles: StateFlow<List<Cycle>> = _cycles.asStateFlow()

    fun getAllDefaultCycles() {
        _stateLoading.value = true
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            CycleRepo.get().getAllFullEntityTemplate().map {
                it.map { e ->
                    Cycle.mapFullFromDbEntity(e)
                }

            }.collect { list ->
                _cycles.value = list
                _stateLoading.value = false
            }
        }
    }
}