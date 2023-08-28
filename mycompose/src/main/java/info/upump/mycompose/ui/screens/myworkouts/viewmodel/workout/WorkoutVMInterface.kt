package info.upump.mycompose.ui.screens.myworkouts.viewmodel.workout

import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Workout
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

interface WorkoutVMInterface {
    val item: StateFlow<Workout>
    val id: StateFlow<Long>
    val img: StateFlow<String>
    val title: StateFlow<String>
    val comment: StateFlow<String>
    val startDate: StateFlow<String>
    val finishDate: StateFlow<String>
    val isTitleError: StateFlow<Boolean>
    val day: StateFlow<Day>

    fun getBy(id: Long)
    fun save(callback: (id: Long)-> Unit)
    fun saveWith(parentId: Long, callback: (id: Long) -> Unit)
    fun updateTitle(title: String)
    fun updateStartDate(date: Date)
    fun updateFinishDate(date: Date)
    fun updateComment(comment: String)
    fun updateDay(it: Day)
    fun collectToSave(): Workout
    fun isBlankFields(): Boolean
    fun updateId(id: Long)
    fun updateEven(it: Boolean)
}