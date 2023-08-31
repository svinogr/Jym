package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.WorkoutDetailVMInterface
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleDetailVM
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.workout.WorkoutDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.ExerciseItemCard

@Composable
fun ListExercise(
    list: List<Exercise>,
    lazyListState: LazyListState,
    navhost: NavHostController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(R.color.colorBackgroundCardView)),
        state = lazyListState
    ) {
        list.forEachIndexed {index, it ->
            item {
                Column(modifier = Modifier ) {
                    ExerciseItemCard(exercise = it, navHost = navhost)
                    if(index < list.size) {
                        Divider(
                            modifier = Modifier.padding(start = 32.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .padding(end = 8.dp)
                                .background(Color.Black)
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListExercisePreview() {
    val nav = NavHostController(LocalContext.current)
    ListExercise(
        WorkoutDetailVM.vmOnlyForPreview.subItems.collectAsState().value,
        LazyListState(),
        nav
    )
}