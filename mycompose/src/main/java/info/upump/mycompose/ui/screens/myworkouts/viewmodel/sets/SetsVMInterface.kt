package info.upump.mycompose.ui.screens.myworkouts.viewmodel.sets

import info.upump.mycompose.models.entity.Sets
import kotlinx.coroutines.flow.StateFlow

interface SetsVMInterface {
    val item: StateFlow<Sets>
}