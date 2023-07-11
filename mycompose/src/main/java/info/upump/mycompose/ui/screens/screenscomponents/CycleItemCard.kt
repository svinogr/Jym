package info.upump.mycompose.ui.screens.screenscomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.upump.mycompose.models.entity.Cycle

class SampleCycleProvider : PreviewParameterProvider<Cycle> {
    override val values =  sequenceOf(Cycle(title = "Новая", image = "uk1"))


}

@Preview(showBackground = true)
@Composable
fun CycleItemCard(@PreviewParameter(SampleCycleProvider::class) cycle: Cycle) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            val id: Int?

            if(cycle.isDefaultType) {
              id = context.resources.getIdentifier(cycle.defaultImg, "drawable", context.packageName)
            } else if(cycle.image !== null){
                id = context.resources.getIdentifier(cycle.image, "drawable", context.packageName)
            } else{
                id = context.resources.getIdentifier(cycle.defaultImg, "drawable", context.packageName)
            }

            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .width(50.dp),

                painter = painterResource(id = id), contentDescription = "image",
                contentScale = ContentScale.Crop
                // painter = painterResource(id = R.drawable.my_cycle), contentDescription = "sdwdwd"
            )

            Column() {
                Text(text = cycle.title!!, modifier = Modifier, fontSize = 16.sp, maxLines = 1)
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(end = 8.dp)
                    .background(Color.Black))
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