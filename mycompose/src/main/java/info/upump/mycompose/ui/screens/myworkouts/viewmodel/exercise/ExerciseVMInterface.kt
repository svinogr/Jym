package info.upump.mycompose.ui.screens.myworkouts.viewmodel.exercise

import info.upump.mycompose.models.entity.Sets
import kotlinx.coroutines.flow.StateFlow

interface ExerciseVMInterface {
    val subItems: StateFlow<List<Sets>>
    fun getBy(id: Long)
    fun deleteSub(id: Long)
    fun cleanItem()
}