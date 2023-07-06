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
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle

@Composable
fun CycleItemCard(cycle: Cycle) {
    //val cycle = Cycle()
    val context = LocalContext.current
 //   val id = context.resources.getIdentifier(cycle.image, "drawable", context.packageName)
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(0.dp)
      ) {


        Row() {
            val id = context.resources.getIdentifier(cycle.image, "drawable", context.packageName)
            Image(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .padding(4.dp),
                painter = painterResource(id = id), contentDescription = "image",
                contentScale = ContentScale.Crop
               // painter = painterResource(id = R.drawable.my_cycle), contentDescription = "sdwdwd"
            )

          Column {
              Text(text = cycle.title!!)
              Box(modifier = Modifier
                  .fillMaxWidth()
                  .height(1.dp)
                  .padding(end = 4.dp)
                  .background(Color.Black),)
              Box(modifier = Modifier
                  .align(Alignment.End)
                  .padding(end = 4.dp)) {
                  Text(text = cycle.finishStringFormatDate)
              }
          }

        }
    }
}