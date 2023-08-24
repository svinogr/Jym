package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Entity
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleVMCreateEdit
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.VMInterface
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.workout.WorkoutVM
import info.upump.mycompose.ui.theme.MyTextTitleLabelWithColor

@Preview(showBackground = true)
@Composable
fun CardTitlePreviewCycle() {
    val cycle = CycleVMCreateEdit.vmOnlyForPreview
    CardTitle(cycle)
}
@Preview(showBackground = true)
@Composable
fun CardTitlePreviewWorkout() {
    val cycle = WorkoutVM.vmOnlyForPreview
    CardTitle(cycle)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun<T: Entity> CardTitle(
    modelVM: VMInterface<T>,
    modifierCard: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp, end = 4.dp, top = 4.dp),
    modifierValue: Modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp, end = 8.dp, bottom = 4.dp)
) {
    Card(
        modifier = modifierCard,
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        val title by modelVM.title.collectAsState()

        TextField(modifier = modifierValue,
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = colorResource(R.color.colorBackgroundCardView)
            ),
            value = title,
            onValueChange = {
                modelVM.updateTitle(it)
                modelVM.isBlankFields()
            },
            label = {
                Text(
                    text = stringResource(id = R.string.label_title_common),
                    style = MyTextTitleLabelWithColor
                )
            },
            isError = modelVM.isTitleError.collectAsState().value,
            supportingText = {
                             if(modelVM.isTitleError.collectAsState().value){
                               Text(text = "пустое поле")
                             }
            },
            placeholder = {
                Text(text = stringResource(id = R.string.label_title_common))
            }
        )
    }
}
