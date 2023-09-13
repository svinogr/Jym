package info.upump.mycompose.ui.screens.screenscomponents

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.ui.screens.screenscomponents.screen.CardDescription

@Composable
fun BottomSheet(text: String, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .verticalScroll(scrollState)){

        CardDescription(textDescription = text)
        //Text(text = text)
    }

}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BottomSheetPreview() {
    BottomSheet("И нет сомнений, что сторонники тоталитаризма в науке и по сей день остаются уделом либералов, которые жаждут быть представлены в исключительно положительном свете. В целом, конечно, укрепление и развитие внутренней структуры способствует повышению качества вывода текущих активов. Предварительные выводы неутешительны: высококачественный прототип будущего проекта прекрасно подходит для реализации распределения внутренних резервов и ресурсов.")
}