package info.upump.jym.ui.screens.viewmodel.exercise

import info.upump.jym.models.entity.Exercise
import kotlinx.coroutines.flow.StateFlow

interface ExerciseReviewVMInterface {
    val item: StateFlow<Exercise>
    fun getItem(id: Long)
}