package info.upump.jym.ui.screens.viewmodel.exercise

import info.upump.jym.models.entity.Sets
import kotlinx.coroutines.flow.StateFlow

interface ExerciseVMInterface {
    val subItems: StateFlow<List<Sets>>
    fun getBy(id: Long)
    fun deleteSub(id: Long)
    fun cleanItem()
}