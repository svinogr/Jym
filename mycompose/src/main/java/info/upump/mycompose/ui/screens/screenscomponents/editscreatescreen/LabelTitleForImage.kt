package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Entity
import info.upump.mycompose.ui.theme.MyTextTitleLabel20StrokeText

@Composable
fun LabelTitleForImage(
    entity: Entity, alignmentText: Alignment = Alignment.BottomStart
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val modifier1 = Modifier.padding(bottom = 16.dp, start = 10.dp).align(alignmentText)
        Text(
            modifier = modifier1,
            style = MyTextTitleLabel20StrokeText,
            text = if (entity.title!!.isBlank()) {
                stringResource(id = R.string.title_cycle_hint)
            } else {
                entity.title!!
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 1)
@Composable
fun LabelTitleForImagePreview() {
    val cycle = Cycle()
    cycle.title = "Новая программа2"
    LabelTitleForImage(cycle)
}