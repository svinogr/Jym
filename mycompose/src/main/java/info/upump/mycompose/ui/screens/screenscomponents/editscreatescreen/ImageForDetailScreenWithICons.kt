package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ChipDefaults
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.BitmapCreator
import info.upump.mycompose.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageForDetailScreenWithICons(
    image: String,
    defaultImage: String,
    iconInfo: Int = R.drawable.ic_info_black_24dp,
    actionInfo: () -> Unit,
    iconView: Int = R.drawable.ic_yey,
    actionView: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val bitmap: Bitmap
    if (!image.isBlank()) {
        bitmap = BitmapCreator.getImgBitmap(image, context)
    } else if (!defaultImage.isBlank()) {
        bitmap = BitmapCreator.getImgDefaultBitmap(defaultImage, context)
    } else {
        bitmap = BitmapCreator.getExceptionDefaultBitmap(context)
    }

    Box(modifier = modifier) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Row(modifier = Modifier.align(Alignment.BottomEnd)) {
            AssistChip(
                onClick = { actionInfo },
                label = { Text(stringResource(id = R.string.label_description_cycle)) },
                modifier = Modifier.padding(end = 8.dp),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = colorResource(id = R.color.colorAccentNet)
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = iconInfo),
                        " ",
                        Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )

            AssistChip(
                onClick = { actionView },
                label = { Text(stringResource(id = R.string.to_view_workout)) },
                modifier = Modifier.padding(end = 8.dp),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = colorResource(id = R.color.colorAccentNet)
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = iconView),
                        " ",
                        Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )

        }
    }
}

@Preview
@Composable
fun ImageForDetailScreenPreview() {
    ImageForDetailScreenWithICons(
        image = CycleDetailVM.vmOnlyForPreview.img.collectAsState().value,
        defaultImage =
        CycleDetailVM.vmOnlyForPreview.imgDefault.collectAsState().value,
        actionInfo = ::println, actionView = ::println
    )
}
