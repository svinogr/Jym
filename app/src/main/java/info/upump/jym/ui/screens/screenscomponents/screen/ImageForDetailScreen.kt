package info.upump.jym.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import info.upump.jym.ui.screens.screenscomponents.BitmapCreator
import info.upump.jym.ui.screens.viewmodel.cycle.CycleDetailVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageForDetailScreen(
    image: String,
    defaultImage: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = BitmapCreator.getImageWithUri(image, defaultImage),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun ImageForDetailScreenPreview() {
    ImageForDetailScreen(
        image = info.upump.jym.ui.screens.viewmodel.cycle.CycleDetailVM.vmOnlyForPreview.img.collectAsState().value,
        defaultImage =
        info.upump.jym.ui.screens.viewmodel.cycle.CycleDetailVM.vmOnlyForPreview.imgDefault.collectAsState().value,
    )
}
