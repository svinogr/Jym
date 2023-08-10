package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.models.entity.Cycle

@Composable
fun ImageTitleImageTitle(cycle: Cycle) {
    Column {
        var title by remember {
            mutableStateOf(cycle)
        }
        Log.d("state", "1 $title")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

            Log.d("State", "2 ${cycle.title}")
            ImageWithPicker(cycle = cycle)

            LabelTitleForImage(entity = title)
        }

        CardTitle(cycle = title)
    }
}

@Preview(showBackground = false)
@Composable
fun ImageTitleImageTitlePreview() {
    val cycle = Cycle().apply {
        title = "Новая программа"
        defaultImg = "drew"
    }

    ImageTitleImageTitle(cycle)
}


