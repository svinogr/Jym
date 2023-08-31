package info.upump.mycompose.ui.screens.myworkouts.viewmodel.sets

import androidx.lifecycle.viewModelScope
import info.upump.database.repo.SetsRepo
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetsVM : BaseVMWithStateLoad() {
    private val _sets = MutableStateFlow<List<Sets>>(listOf())
    val sets: StateFlow<List<Sets>> = _sets.asStateFlow()

    fun getBy(parentId: Long) {
        _stateLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val repo = SetsRepo.get()
            val sets = repo.getAllByParent(parentId).map {list ->
                list.map {
                    Sets.mapFromDbEntity(it)
                }
            }.collect(){
                _sets.update { it }
            }

            _stateLoading.value = false
        }
    }
}
