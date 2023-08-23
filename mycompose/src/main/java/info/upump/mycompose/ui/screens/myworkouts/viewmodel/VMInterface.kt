package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import info.upump.mycompose.models.entity.Day
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

interface VMInterface<T> {
    val item: StateFlow<T>
    val id: StateFlow<Long>
    val imgOption: StateFlow<String>
    val title: StateFlow<String>
    val comment: StateFlow<String>
    val startDate: StateFlow<String>
    val finishDate: StateFlow<String>
    val day: StateFlow<Day>
    val isTitleError: StateFlow<Boolean>

    fun getBy(id: Long)
    fun save(callback: (id: Long)-> Unit)
    fun saveWith(parentId: Long, callback: (id: Long) -> Unit)
    fun updateTitle(title: String)
    fun updateImage(imgStr: String)
    fun updateStartDate(date: Date)
    fun updateFinishDate(date: Date)
    fun updateComment(comment: String)
    fun updateDay(it: Day)
    fun collectToSave(): T
    fun isBlankFields(): Boolean
    fun updateId(id: Long)
}