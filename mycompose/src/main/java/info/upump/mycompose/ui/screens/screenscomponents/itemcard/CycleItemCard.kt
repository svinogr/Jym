package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.theme.MyTextLabel12
import info.upump.mycompose.ui.theme.MyTextTitleLabel16

@Composable
fun CycleItemCard(
    cycle: Cycle,
    navHost: NavHostController,
    context: Context,
    modifier: Modifier = Modifier
) {
    val bitmap: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(1.dp)
            .clickable {
                navHost.navigate(NavigationItem.DetailCycleNavigationItem.routeWithId(cycle.id))
            },
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {

            //  bitmap = image.value.asImageBitmap(),
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .width(50.dp)
            ) {
                /*bitmap.value?.let {
                    Image(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .clip(CircleShape),
                        bitmap = it.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = ""
                    )
                }*/
                //------------
                if (!cycle.image.isBlank()) {
                    Image(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .clip(CircleShape),
                        painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = Uri.parse(cycle.image))
                                .build()
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = ""
                    )

                }
                //----------
                var image = ""
                var imageDef = ""
                if (cycle.isDefaultType) {
                    image = ""
                    imageDef = cycle.imageDefault
                } else {
                    image = cycle.image
                    imageDef = ""
                }
                ItemImage(
                    image = image,
                    defaultImage = imageDef
                )


            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                val modifierCol = Modifier
                    .padding(end = 8.dp)
                Text(
                    text = cycle.title!!,
                    style = MyTextTitleLabel16,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.fillMaxWidth()
                )

                if (!cycle.isDefaultType) {
                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .background(Color.Black)
                    )
                }

                if (!cycle.isDefaultType) {
                    Box(
                        modifier = modifierCol
                            .align(Alignment.End).padding(top = 4.dp)
                    ) {
                        Text(
                            text = cycle.finishStringFormatDate,
                            style = MyTextLabel12,
                        )
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCycleItemCard() {
    val c = Cycle().apply {
        title = "Новая"
        imageDefault = ""
        image = ""
    }
    CycleItemCard(c, NavHostController(LocalContext.current), LocalContext.current)
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewCycleItemCard2() {
    val c = Cycle().apply {
        title = "Новая2"
        imageDefault = ""
        image = ""
    }
    CycleItemCard(c, NavHostController(LocalContext.current), LocalContext.current)
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewCycleItemCard3() {
    val c = Cycle().apply {
        isDefaultType = true
        title = "Новая3"
        imageDefault = "plint1"
        image = "content://media/picker/0/com.android.providers.media.photopicker/media/1000000033"
    }
    CycleItemCard(c, NavHostController(LocalContext.current), LocalContext.current)
}