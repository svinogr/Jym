package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import info.upump.mycompose.models.entity.Day
import kotlinx.coroutines.flow.StateFlow

interface VMDetailInterface<T, R> {
    val item: StateFlow<T>
    val subItems: StateFlow<List<R>>
    val id: StateFlow<Long>
    val img: StateFlow<String>
    val imgDefault: StateFlow<String>
    val title: StateFlow<String>
    val comment: StateFlow<String>
    val startDate: StateFlow<String>
    val finishDate: StateFlow<String>
    val day: StateFlow<Day>
    fun getInitialItem(id: Long)
}