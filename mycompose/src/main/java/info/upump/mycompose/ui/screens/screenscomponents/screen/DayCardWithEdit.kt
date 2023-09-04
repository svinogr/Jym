package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Day

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayCardWorkoutEdit(
    day: Day,
    updateDay: (Day) -> Unit,
    isEven: Boolean,
    updateEven: (Boolean) -> Unit,
    modifier: Modifier = Modifier, focus: FocusManager,

    ) {
    val modifierCard = modifier
        .fillMaxWidth()

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
        Box(
            modifier = Modifier              // для фона
                .fillMaxWidth()
                .background(brush, alpha = 0.30f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            )
            {
                ExposedDropdownMenuBox(modifier = Modifier.weight(1f),
                    expanded = expanded, onExpandedChange = {
                        expanded = !expanded
                        focus.clearFocus()
                    }) {
                    AssistChip(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f)
                            .menuAnchor(),
                        onClick = {
                        },
                        label = {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    stringResource(id = day.title()),
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = colorResource(id = R.color.colorBackgroundChips)
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendar),
                                " ",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = 0.dp, start = 8.dp)
                            .background(Color.Transparent)
                    ) {
                        ExposedDropdownMenu(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .background(brush, alpha = 0.30f),
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }) {
                            Day.values().forEach {
                                DropdownMenuItem(modifier = Modifier,
                                    onClick = {
                                        updateDay(it)
                                        expanded = false
                                    }) {
                                    AssistChip(
                                        modifier = Modifier,
                                        onClick = {
                                            updateDay(it)
                                            expanded = false
                                        },
                                        label = {
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .fillMaxWidth()
                                            ) {
                                                Text(
                                                    stringResource(id = it.title()),
                                                    modifier = Modifier.align(Alignment.Center)
                                                )
                                            }
                                        },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = colorResource(id = R.color.colorBackgroundChips)
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                AssistChip(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    onClick = { updateEven(!isEven) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = colorResource(id = R.color.colorBackgroundChips)
                    ),
                    label = {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            if (isEven) {
                                Text(
                                    stringResource(id = R.string.week_even),
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            } else {
                                Text(
                                    stringResource(id = R.string.week_not_even),
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    },
                    leadingIcon = if (isEven) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    } else {
                        { Spacer(modifier = Modifier.size(AssistChipDefaults.IconSize)) } // TODO может убоать и оставить null?
                    },
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DayCardWorkoutEditPreview() {

    DayCardWorkoutEdit(Day.TUESDAY, ::println, true, ::println, focus = LocalFocusManager.current)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DayCardWorkoutEditPreview1() {
    DayCardWorkoutEdit(Day.FRIDAY, ::println, true, ::println, focus = LocalFocusManager.current)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DayCardWorkoutEditPreview2() {

    DayCardWorkoutEdit(
        Day.SATURDAY,
        ::println,
        false,
        ::println,
        focus = LocalFocusManager.current
    )
}


fun Day.next(): Day {
    val values = Day.values()
    val next = (ordinal + 1) / values.size
    return values[next]
}