package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVMInterface

@Composable
fun ImageTitleImageTitle(cycleVM: CycleVMInterface) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            ImageWithPicker(cycleVM)
           LabelTitleForImage(cycleVM)
        }

        CardTitle(cycleVM)
    }
}

@Preview(showBackground = false)
@Composable
fun ImageTitleImageTitlePreview() {
    val cycle = CycleVM.vmOnlyForPreview
    ImageTitleImageTitle(cycle)
}


