package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.SetsRepo
import info.upump.mycompose.models.entity.Sets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SetsVM : BaseVMWithStateLoad(){
    private val _sets = MutableStateFlow<List<Sets>>(listOf())
    val sets: StateFlow<List<Sets>> = _sets.asStateFlow()

    fun getSets(id: Long) {
        viewModelScope.launch(Dispatchers.IO){
            _stateLoading.value = true
            val setsRepo = SetsRepo.get()
            val setsGet = setsRepo.getAllByParent(id).map { Sets.mapFromDbEntity(it) }

            _sets.value = setsGet
            _stateLoading.value = false
        }
    }
}
