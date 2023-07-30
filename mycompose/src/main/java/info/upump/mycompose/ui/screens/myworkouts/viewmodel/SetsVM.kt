package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.util.Log
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

    private val _set = MutableStateFlow<Sets>(Sets())
    val set: StateFlow<Sets> = _set.asStateFlow()


    fun getSetsByParent(id: Long) {
        viewModelScope.launch(Dispatchers.IO){
            _stateLoading.value = true
            val setsRepo = SetsRepo.get()
            val setsGet = setsRepo.getAllByParent(id).map { Sets.mapFromDbEntity(it) }

            _sets.value = setsGet
            _stateLoading.value = false
        }
    }

    fun getSetsBy(id: Long) {
        if (id == 0L){
            val set = Sets()
            _set.value = set

            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true
            val setsRepo = SetsRepo.get()
            val setsGet = Sets.mapFromDbEntity(setsRepo.getBy(id))
            Log.d("initialState", setsGet.toString())

            _set.value = setsGet
            _stateLoading.value = false
        }
    }
}
