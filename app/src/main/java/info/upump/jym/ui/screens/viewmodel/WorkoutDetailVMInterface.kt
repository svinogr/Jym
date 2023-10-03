package info.upump.jym.ui.screens.viewmodel

import info.upump.jym.models.entity.Day
import info.upump.jym.models.entity.Exercise
import info.upump.jym.models.entity.Workout
import kotlinx.coroutines.flow.StateFlow

interface WorkoutDetailVMInterface {
    val item: StateFlow<Workout>
    val id: StateFlow<Long>
    val title: StateFlow<String>
    val comment: StateFlow<String>
    val startDate: StateFlow<String>
    val finishDate: StateFlow<String>
    val day: StateFlow<Day>
    val isEven: StateFlow<Boolean>
    val subItems: StateFlow<List<Exercise>>
    fun getBy(id: Long)
    fun delete(it: Long)
    fun deleteSub(it: Long)

    fun cleanItem()
}
