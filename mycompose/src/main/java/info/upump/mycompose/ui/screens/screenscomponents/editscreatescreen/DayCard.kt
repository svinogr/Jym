package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.VMInterface
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.WorkoutVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayCard(
    modelVM: VMInterface<Workout>,
    modifierCard: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp, end = 4.dp, top = 4.dp),
    modifierValue: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
) {
    val workout by modelVM.item.collectAsState()
    var expanded by remember {
        mutableStateOf(true)
    }
    Card(
        modifier = modifierCard,
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            // ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            //      val weekEvenEl = createRef()
            //       val dayEl = createRef()
            //        val gui = createGuidelineFromStart(0.7f)

            Box(  modifier = Modifier.weight(1f)) {
                ExposedDropdownMenuBox(
                    expanded = expanded, onExpandedChange = {
                        expanded = !expanded
                    }) {

                    TextField(modifier = modifierValue
                        .menuAnchor(),
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            containerColor =   colorResource(id = R.color.colorBackgroundCardView)

                        ),
                        readOnly = true,
                        value = workout.day!!.toString(),
                        onValueChange = {
                        }
                    )

                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
                        expanded = false
                    }) {
                        Day.values().forEach {
                            DropdownMenuItem(
                                onClick = {
                                    modelVM.updateDay(it)
                                    expanded = false
                                }) {
                                Text(it.toString())
                            }
                        }
                    }
                }
            }
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {  }) {
                Text("Четная")
            }
        }

    }


}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DayCardPreview() {
    val m = WorkoutVM.vmOnlyForPreview
    DayCard(m)
}