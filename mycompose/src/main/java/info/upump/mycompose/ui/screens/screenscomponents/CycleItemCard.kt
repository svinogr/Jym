package info.upump.mycompose.ui.screens.screenscomponents

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.theme.MyTextTitleLabel16

class SampleCycleProvider : PreviewParameterProvider<Cycle> {
    override val values = sequenceOf(Cycle(title = "Новая", image = "uk1"))
}


/*
fun getImage(cycle: Cycle, context: Context): Int {

    return if (cycle.defaultImg != null) {

        context.resources.getIdentifier(
            cycle.defaultImg,
            "drawable",
            context.packageName
        )
    } else {
        context.resources.getIdentifier(
            "drew",
            "drawable",
            context.packageName
        )
    }
}
*/
@RequiresApi(Build.VERSION_CODES.P)
fun getImage(cycle: Cycle, context: Context): Bitmap {

    val name = context.packageName
    var source: ImageDecoder.Source
    Log.d("getImage", "${cycle.defaultImg}")
    try {
        if (cycle.image != null) {
            source = ImageDecoder.createSource(context.contentResolver, Uri.parse(cycle.image))

        } else {
            val id = context.resources.getIdentifier(cycle.defaultImg, "drawable", name)
            source = ImageDecoder.createSource(context.resources, id)

        }
    } catch (e: NullPointerException) {
        val id = context.resources.getIdentifier("drew", "drawable", name)
        source = ImageDecoder.createSource(context.resources, id)

    }

    return ImageDecoder.decodeBitmap(source)
}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CycleItemCard(
    @PreviewParameter(SampleCycleProvider::class) cycle: Cycle,
    navHost: NavHostController
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
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

            AsyncImage( modifier = Modifier
                .padding(8.dp)
                .height(50.dp)
                .width(50.dp)
                .clip(CircleShape),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(context)
                .data(getImage(cycle, context))
                .crossfade(true)
                .build(), contentDescription ="" )


       /*     Image(
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .width(50.dp)
                    .clip(CircleShape),
                //    painter = painterResource(id = getImage(cycle, context)),
                bitmap = getImage(cycle, context, scop).asImageBitmap(),
                contentDescription = "image",
                contentScale = ContentScale.Crop
                // painter = painterResource(id = R.drawable.my_cycle), contentDescription = "sdwdwd"
            )*/
// TODO сделать асинхронку
      /*            GlideImage(
                      modifier = Modifier
                          .padding(8.dp)
                          .height(50.dp)
                          .width(50.dp)
                          .clip(CircleShape),
                  //    painter = painterResource(id = getImage(cycle, context)),
                      model =
                      contentDescription = "image",
                      contentScale = ContentScale.Crop

                      // painter = painterResource(id = R.drawable.my_cycle), contentDescription = "sdwdwd"
                  )
*/
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = cycle.title!!,
                    style = MyTextTitleLabel16,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (!cycle.isDefaultType) {
                    Divider(
                        modifier = Modifier

                            .height(1.dp)
                            .padding(end = 8.dp)
                            .background(Color.Black)
                    )
                }

                if (!cycle.isDefaultType) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 8.dp, top = 4.dp)
                    ) {
                        Text(
                            text = cycle.finishStringFormatDate,
                            fontSize = 12.sp,
                            color = Color(0xFF6c6c70)
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
    val c = Cycle(title = "Новая", image = "uk1")
    CycleItemCard(c, NavHostController(LocalContext.current))
}