package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ImageTitleImageTitle(title: String, isTitleError: Boolean, updateText: (String) -> Unit,  content: @Composable() ()-> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            content()
            LabelTitleForImage(title)
        }
        CardTitle(title, isTitleError, updateText)
    }
}
@Preview(showBackground = false)
@Composable
fun ImageTitleImageTitlePreviewCycleWithDefaultImage() {
    val f: (String) -> Unit = { println(it) }
        ImageTitleImageTitle("its a title", true, f){
       ImageWithPicker("", "uk1", ::println)
    }
}

