package info.upump.mycompose.ui.screens.screenscomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Entity
import info.upump.mycompose.ui.theme.MyTextTitleLabel20StrokeText

@Composable
fun LabelTitleForImage(entity: Entity, modifier: Modifier) {
    val title by remember {
        mutableStateOf(entity)
    }

    Text(
        modifier = modifier,
        style = MyTextTitleLabel20StrokeText,
        text = if (title.title!!.isBlank()) {
            stringResource(id = R.string.title_cycle_hint)
        } else {
            title.title!!
        }
    )
}

@Preview(showBackground = true, backgroundColor = 1)
@Composable
fun LabelTitleForImagePreview() {
    val labelTitleBoxModifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp, start = 10.dp, end = 10.dp)
    val cycle = Cycle()
    cycle.title = "Новая программа"
    LabelTitleForImage(cycle, modifier = labelTitleBoxModifier)
}