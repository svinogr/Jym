package info.upump.mycompose.ui.screens.myworkouts.viewmodel.exercise

import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExerciseVM: BaseVMWithStateLoad(),  ExerciseVMInterface {


    private val _listSets = MutableStateFlow<List<Sets>>(
        mutableListOf(Sets(),Sets(), Sets())
    )

    override val subItems: StateFlow<List<Sets>> =  _listSets

    override fun getBy(id: Long) {
        TODO("Not yet implemented")
    }

    companion object  {
      val vmOnlyForPreview by lazy {
        object : ExerciseVMInterface {

            private val _listSets = MutableStateFlow<List<Sets>>(
                mutableListOf(Sets(),Sets(), Sets())
            )

            override val subItems: StateFlow<List<Sets>>  = _listSets.asStateFlow()


            override fun getBy(id: Long) {

            }

        }

      }

    }
}