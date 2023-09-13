package info.upump.mycompose.ui.screens.myworkoutsscreens.viewmodel.exercise

import info.upump.mycompose.models.entity.Exercise
import kotlinx.coroutines.flow.StateFlow

interface ExerciseReviewVMInterface {
    val item: StateFlow<Exercise>
    fun getItem(id: Long)
}