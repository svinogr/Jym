package info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle

import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Workout
import kotlinx.coroutines.flow.StateFlow

interface CycleDetailVMInterface {
    val item: StateFlow<Cycle>
    val id: StateFlow<Long>
    val title: StateFlow<String>
    val comment: StateFlow<String>
    val startDate: StateFlow<String>
    val finishDate: StateFlow<String>
    val img: StateFlow<String>
    val imgDefault: StateFlow<String>
    val subItems: StateFlow<List<Workout>>
    fun getBy(id: Long)
    fun deleteSubItem(id: Long)
}