package info.upump.mycompose.ui.screens.screenscomponents

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.theme.MyTextTitleLabel16
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/*fun getImage(cycle: Cycle, context: Context): Bitmap {
   var bitmap: Bitmap
   val name = context.packageName

   var source: ImageDecoder.Source
   Log.d("image", " = 1) ${cycle.image} ${cycle.title}")
   try {
       if (!cycle.image.isBlank()) {
           Log.d("image", " = 2) ${cycle.image} ${cycle.title}")
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
               source = ImageDecoder.createSource(context.contentResolver, Uri.parse(cycle.image))
               bitmap = ImageDecoder.decodeBitmap(source)
           } else {
               bitmap = MediaStore.Images.Media.getBitmap(
                   context.contentResolver,
                   Uri.parse(cycle.image)
               );
           }
       } else {
           val id = context.resources.getIdentifier(cycle.defaultImg, "drawable", name)
           Log.d("image", " = 2a) ${cycle.defaultImg} ${cycle.title}")
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
               source = ImageDecoder.createSource(context.resources, id)
               bitmap = ImageDecoder.decodeBitmap(source)
           } else {
               bitmap = BitmapFactory.decodeResource(context.resources, id);
           }
       }
   } catch (e: Exception) {
       val id = context.resources.getIdentifier("drew", "drawable", name)
       Log.d("image", " = 2e) ${cycle.defaultImg} ${cycle.title}")
       Log.d("image", " ${e.message}")
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
           source = ImageDecoder.createSource(context.resources, id)
           bitmap = ImageDecoder.decodeBitmap(source)
       } else {
           bitmap = BitmapFactory.decodeResource(context.resources, id);
       }

   }
   return bitmap
}

*/
@Composable
fun CycleItemCard(
    cycle: Cycle,
    navHost: NavHostController,
    context: Context
) {
    val bitmap: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }

    bitmap.value ?: run {
        LaunchedEffect(key1 = true) {
            launch(Dispatchers.IO) {
                bitmap.value = BitmapCreator.getImageBitmap(cycle, context)
            }
        }
    }

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

            //  bitmap = image.value.asImageBitmap(),
            Box(
                modifier = Modifier.padding(8.dp)
                    .height(50.dp)
                    .width(50.dp)
            ) {
                bitmap.value?.let {
                    Image(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .clip(CircleShape),
                        bitmap = it.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = ""
                    )
                }
            }

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
@Preview(showBackground = true)
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
        title = "Новая3"
        imageDefault = "plint1"
        image = ""
    }
    CycleItemCard(c, NavHostController(LocalContext.current), LocalContext.current)
}