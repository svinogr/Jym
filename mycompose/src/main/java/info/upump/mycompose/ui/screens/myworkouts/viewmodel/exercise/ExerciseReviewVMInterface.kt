package info.upump.mycompose.ui.screens.myworkouts.viewmodel.exercise

import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.TypeMuscle
import kotlinx.coroutines.flow.StateFlow

interface ExerciseReviewVMInterface {
    val item: StateFlow<Exercise>
    fun getItem(id: Long)
}