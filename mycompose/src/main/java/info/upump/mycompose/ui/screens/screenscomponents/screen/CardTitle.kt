package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.theme.MyTextTitleLabelWithColor

@Preview(showBackground = true)
@Composable
fun CardTitlePreviewCycleError() {
    val cycle = Cycle().apply {
        title = "its a Title"
    }
    val f: (String) -> Unit = { println(it) }

    CardTitle(cycle.title, true, f)
}

@Preview(showBackground = true)
@Composable
fun CardTitlePreviewCycle() {
    val cycle = Cycle().apply {
        title = "its a Title"
    }
    val f: (String) -> Unit = { println(it) }

    CardTitle(cycle.title, false, f)
}


@Preview(showBackground = true)
@Composable
fun CardTitlePreviewWorkout() {
    val workout = Workout().apply {
        title = "its a Title"
    }
    val f: (String) -> Unit = { println(it) }

    CardTitle(workout.title, false, f)
}

@Preview(showBackground = true)
@Composable
fun CardTitlePreviewWorkoutError() {
    val workout = Workout().apply {
        title = "its a Title"
    }
    val f: (String) -> Unit = { println(it) }

    CardTitle(workout.title, true, f)
}
@Preview(showBackground = true)
@Composable
fun CardTitlePreviewWorkoutNotitle() {
    val workout = Workout().apply {
        title = ""
    }
    val f: (String) -> Unit = { println(it) }

    CardTitle(workout.title, false, f)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardTitle(
    text: String,
    isError: Boolean,
    updateText: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val modifierCard = modifier
        .fillMaxWidth()
        .padding(start = 0.dp, end = 0.dp, top = 0.dp)

    val req = remember{FocusRequester()}

    val modifierValue = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp, bottom = 4.dp)
    Card(
        modifier = modifierCard,
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        Box(modifier = Modifier
            .fillMaxWidth().padding(start = 4.dp, end = 4.dp)) {
            TextField(modifier = modifierValue.focusRequester(req),
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor =  colorResource(R.color.colorBackgroundChips),
                    unfocusedIndicatorColor = colorResource(R.color.colorBackgroundChips),
                    disabledIndicatorColor = colorResource(R.color.colorBackgroundChips),
                    containerColor = colorResource(R.color.colorBackgroundCardView)
                ),
                value = text,
                onValueChange = {
                    updateText(it)
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.label_title_common),
                        style = MyTextTitleLabelWithColor
                    )
                },
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(text = stringResource(id = R.string.error_tips_empty_field))
                    }
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.label_title_common))
                }

            )
        }
    }
}
