package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.models.entity.Entity
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVMCreateEdit
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.VMInterface
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.WorkoutVM

@Composable
fun <T: Entity> ImageTitleImageTitle(modelVM: VMInterface<T>, content: @Composable() ()-> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            content()
            LabelTitleForImage(modelVM)
        }
        CardTitle(modelVM)
    }
}
@Preview(showBackground = false)
@Composable
fun ImageTitleImageTitlePreviewCycle() {
    val cycle = CycleVMCreateEdit.vmOnlyForPreview
    ImageTitleImageTitle(cycle){
        ImageWithPicker(cycle)
    }
}


@Preview(showBackground = false)
@Composable
fun ImageTitleImageTitlePreviewWorkout() {
    val workout = WorkoutVM.vmOnlyForPreview
    ImageTitleImageTitle(workout) {
        ImageByDay(workout)
    }
}
