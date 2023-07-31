package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.SetsRepo
import info.upump.mycompose.models.entity.Sets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.map
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
            setsRepo.getAllByParent(id).map {

                it.map { item -> Sets.mapFromDbEntity(item) }
            }.collect{
                _sets.value = it
            }


          //  _sets.value = setsGet
            _stateLoading.value = false
        }
    }

 /*   fun getSetsBy(id: Long) {
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
    }*/

 /*   fun saveItem(item: Sets) {
        viewModelScope.launch(Dispatchers.IO) {
            val setsRepo = SetsRepo.get()
            val setsGet = Sets.mapTontity(item)
            if (item.id != 0L) {
                setsRepo.update(setsGet)
            } else {
                setsRepo.save(setsGet)
            }
            Log.d("saveItem", "$setsGet ")
        }
    }*/
}
