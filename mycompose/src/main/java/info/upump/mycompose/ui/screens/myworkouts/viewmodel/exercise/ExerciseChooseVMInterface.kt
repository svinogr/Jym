package info.upump.mycompose.ui.screens.myworkouts.viewmodel.exercise

import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Exercise
import kotlinx.coroutines.flow.StateFlow

interface ExerciseChooseVMInterface {
    val day: StateFlow<Day>
    val subItems: StateFlow<List<Exercise>>
    fun getAllExerciseForParent(parent: Long)
    fun saveForParentChosen(id: Long)

}
