package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@Composable
fun <T: Entity> DescriptionCardWithVM(
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
        val comment by modelVM.comment.collectAsState()

        TextField(modifier = modifierValue,
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                backgroundColor = colorResource(R.color.colorBackgroundCardView)
            ),
            value = comment,
            onValueChange = {
                modelVM.updateComment(it)
             //   comment = it
            },
            label = {
                Text(
                    text = stringResource(id = R.string.label_description_cycle),
                    style = MyTextTitleLabelWithColor
                )
            },
            placeholder = {
                Text(text = stringResource(id = R.string.label_description_cycle))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DescriptionCardWithVMPreview() {
    val vm = CycleVMCreateEdit.vmOnlyForPreview
    DescriptionCardWithVM(vm)
}

@Preview(showBackground = true)
@Composable
fun DescriptionCardWithVMPreview2() {
    val vm = WorkoutVM.vmOnlyForPreview
    DescriptionCardWithVM(vm)
}