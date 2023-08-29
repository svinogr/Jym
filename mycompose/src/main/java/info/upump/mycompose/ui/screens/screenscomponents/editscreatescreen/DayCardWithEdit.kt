package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.ui.theme.MyTextLabel12

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayCardWorkoutEdit(
    day: Day,
    updateDay: (Day) -> Unit,
    isEven: Boolean,
    updateEven: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val modifierCard = modifier
        .fillMaxWidth()
        .padding(start = 0.dp, end = 0.dp, top = 4.dp)
    val modifierValue = Modifier
        .fillMaxWidth()
        .padding(start = 0.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
    var expanded by remember {
        mutableStateOf(false)
    }
    val colorDay = colorResource(day.getColor())

    val brush = Brush.horizontalGradient(
        colorStops = arrayOf(
            0.0f to Color.White,
            0.5F to colorDay
        ), 0.10f, Float.POSITIVE_INFINITY, TileMode.Clamp
    )

    Card(
        modifier = modifierCard,
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
        )
    ) {
        Box(modifier = Modifier              // для фона
            .fillMaxHeight()
            .fillMaxWidth()
            .background(brush, alpha = 0.30f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            )
            {
                ExposedDropdownMenuBox(modifier = Modifier.weight(1f),
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
                            containerColor = Color.Transparent

                            // containerColor = Color( brush)
                        ),
                        label = {
                            Text(
                                style = MyTextLabel12,
                                text = stringResource(id = R.string.label_spinner_day)
                            )
                        },
                        readOnly = true,
                        value = stringResource(id = day.title()),
                        onValueChange = {
                        }
                    )

                    ExposedDropdownMenu(
                        modifier = modifierValue.fillMaxHeight(),
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }) {
                        Day.values().forEach {
                            DropdownMenuItem(
                                onClick = {
                                    updateDay(it)
                                    expanded = false
                                }) {
                                Text(stringResource(id = it.title()))
                            }
                        }
                    }
                }

                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp).align(Alignment.CenterVertically),
                    onClick = {
                        Log.d("updateEven!", "$isEven")
                        Log.d("updateEven!", "!$isEven")

                        updateEven(!isEven)
                    }) {
                    if (isEven) {
                        Text("четная")
                    } else {
                        Text("нечетная")
                    }
                }
            }

        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DayCardPreview() {

    DayCardWorkoutEdit(Day.TUESDAY, ::println, true, ::println)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DayCardPreview1() {
    DayCardWorkoutEdit(Day.FRIDAY, ::println, true, ::println)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DayCardPreview2() {

    DayCardWorkoutEdit(Day.SATURDAY, ::println, true, ::println)
}


fun Day.next(): Day {
    val values = Day.values()
    val next = (ordinal + 1) / values.size
    return values[next]
}