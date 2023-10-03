package info.upump.jym.ui.screens.viewmodel.exercise

import info.upump.jym.models.entity.Day
import info.upump.jym.models.entity.Exercise
import kotlinx.coroutines.flow.StateFlow

interface ExerciseChooseVMInterface {
    val day: StateFlow<Day>
    val subItems: StateFlow<List<Exercise>>
    fun getAllExerciseForParent(parent: Long)
    fun saveForParentChosen(id: Long)

}
