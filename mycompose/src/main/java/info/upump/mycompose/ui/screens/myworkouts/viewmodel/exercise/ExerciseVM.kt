package info.upump.mycompose.ui.screens.myworkouts.viewmodel.exercise

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.ExerciseRepo
import info.upump.database.repo.SetsRepo
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseVM : BaseVMWithStateLoad(), ExerciseVMInterface {


    private val _listSets = MutableStateFlow<List<Sets>>(
        mutableListOf()
    )

    override val subItems: StateFlow<List<Sets>> = _listSets

    override fun getBy(id: Long) {
        _stateLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val repo = SetsRepo.get()

            repo.getAllByParent(id).map { listEntity ->
                listEntity.map { it ->
                    Sets.mapFromDbEntity(it)
                }
            }.collect() {
                _listSets.value =  it
            }

            _stateLoading.value = false
        }
    }

    companion object {
        val vmOnlyForPreview by lazy {
            object : ExerciseVMInterface {

                private val _listSets = MutableStateFlow<List<Sets>>(
                    mutableListOf(Sets(), Sets(), Sets())
                )

                override val subItems: StateFlow<List<Sets>> = _listSets.asStateFlow()


                override fun getBy(id: Long) {

                }

            }

        }

    }
}