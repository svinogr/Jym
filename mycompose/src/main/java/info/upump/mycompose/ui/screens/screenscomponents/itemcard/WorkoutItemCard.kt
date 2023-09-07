package info.upump.mycompose.ui.screens.screenscomponents.itemcard

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.theme.MyTextLabel12
import info.upump.mycompose.ui.theme.MyTextTitleLabel16


@Composable
fun WorkoutItemCard(workout: Workout, navHost: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navHost.navigate(NavigationItem.DetailWorkoutNavigationItem.routeWithId(workout.id))
            },
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(
                colorResource(id = R.color.colorBackgroundCardView)
            )
        ) {
            val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(context.getColor(workout.day!!.getColor()))
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                bitmap = bitmap.asImageBitmap(), contentDescription = "image"
            )

            Column(modifier = modifier.fillMaxWidth()) {
                val modifierCol = Modifier.padding(end = 8.dp)
                Text(
                    text = workout.title!!,
                    style = MyTextTitleLabel16,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.fillMaxWidth()
                )
                Box(
                    modifier = modifierCol
                        .align(Alignment.End)
                        .padding( top = 4.dp)
                ) {
                    Text(
                        text = context.getString(workout.day!!.title()),
                        style = MyTextLabel12
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewWorkoutCard() {
    val workout = Workout(
        isWeekEven = false, isDefaultType = false,
        isTemplate = false, day = Day.FRIDAY, exercises = listOf()
    ).apply { title = "Новая" }
    val context = LocalContext.current
    WorkoutItemCard(workout = workout, navHost = NavHostController(context))
}

