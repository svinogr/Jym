package info.upump.mycompose.ui.screens.screenscomponents

import android.graphics.Bitmap
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.theme.MyTextLabel

class SampleWorkoutProvider : PreviewParameterProvider<Workout> {
    override val values = sequenceOf(
        Workout(
            title = "Новая",
            isWeekEven = false, isDefaultType = true,
            isTemplate = false, day = Day.FRIDAY, exercises = listOf()
        )
    )
}


@Composable
fun WorkoutItemCard(workout: Workout, navHost: NavHostController) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navHost.navigate(NavigationItem.DefaultDetailCycleNavigationItem.routeWithId(workout.id))
            },
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(MaterialTheme.colorScheme.background
            )
           ) {

            val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(context.getColor(workout.day!!.getColor()))
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .width(50.dp).
                    clip(CircleShape),
                bitmap = bitmap.asImageBitmap(), contentDescription = "image"
                // painter = painterResource(id = R.drawable.my_cycle), contentDescription = "sdwdwd"
            )

            Column() {
                Text(text = workout.title!!, fontSize = 16.sp, maxLines = 1)
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(end = 8.dp)
                        .background(Color.Black)
                )
                if (workout.isDefaultType) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 8.dp, top = 4.dp)
                    ) {
                        Text(
                            text = context.getString(workout.day!!.title()),
                            style = MyTextLabel
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWorkoutCard() {
    val workout = Workout(
        title = "Новая",
        isWeekEven = false, isDefaultType = false,
        isTemplate = false, day = Day.FRIDAY, exercises = listOf()
    )
    val context = LocalContext.current
    WorkoutItemCard(workout = workout, navHost = NavHostController(context))
}

