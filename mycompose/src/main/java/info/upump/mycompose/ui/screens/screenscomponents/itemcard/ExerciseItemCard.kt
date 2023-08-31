package info.upump.mycompose.ui.screens.screenscomponents.itemcard

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
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.ExerciseDescription
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.theme.MyTextLabel12
import info.upump.mycompose.ui.theme.MyTextTitleLabel16
import java.nio.file.WatchEvent

const val DEFAULT_IMAGE = "drew"

@Composable
fun ExerciseItemCard(exercise: Exercise, navHost: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(1.dp)
            .clickable {
                //TODO
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
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { //TODO
                    },
                painter = if (exercise.exerciseDescription!!.defaultImg != null) {
                    painterResource(
                        context.resources.getIdentifier(
                            exercise.exerciseDescription!!.defaultImg,
                            "drawable",
                            context.packageName
                        )
                    )
                } else {
                    painterResource(
                        id = context.resources.getIdentifier(
                            exercise.exerciseDescription!!.img,
                            "drawable",
                            context.packageName
                        )
                    )
                },
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )

            Column() {
                val modifier = Modifier.padding(end = 8.dp)
                Text(
                    text = exercise.exerciseDescription!!.title!!,
                    style = MyTextTitleLabel16,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier

                )
                Divider(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 8.dp, top = 4.dp)
                ) {
                    Text(
                        text = getExerciseString(exercise),
                        style = MyTextLabel12
                    )
                }
            }
        }
    }
}

fun getExerciseString(exercise: Exercise): String {
    val textResult = StringBuilder()
    val setList = exercise.setsList
    if (setList.size > 1) {
        setList.sortedWith { o1, o2 ->
            if (o1.reps < o2.reps) {
                return@sortedWith -1
            } else if (o1.reps == o2.reps) {
                return@sortedWith 0
            } else return@sortedWith 1
        }
        if (setList[0].reps == setList[setList.size - 1].reps) {
            textResult.append(setList.size).append(" x ").append(setList[0].reps)
        } else textResult.append(setList.size).append(" x ").append(setList[0].reps).append(" - ")
            .append(setList[setList.size - 1].reps)
    } else if (setList.size == 1) {
        textResult.append("1 x ").append(setList[0].reps)

    } else textResult.append(0)

    return textResult.toString()
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewExerciseItemCard() {
    val exerdescription =
        ExerciseDescription(img = "nach1", defaultImg = "nach1", title = "Новое упраж")

    val listSets = listOf(
        Sets(20.0, 10, 15.0),
        Sets(20.0, 10, 15.0),
        Sets(20.0, 10, 15.0)
    )

    val exercise = Exercise(
        isDefaultType = false,
        isTemplate = false, exerciseDescription = exerdescription
    ).apply { title = "Новое упраж" }

    exercise.setsList = listSets
    ExerciseItemCard(exercise = exercise, navHost = NavController(LocalContext.current))

}