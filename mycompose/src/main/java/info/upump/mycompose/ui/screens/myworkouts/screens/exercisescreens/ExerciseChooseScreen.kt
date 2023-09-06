package info.upump.mycompose.ui.screens.myworkouts.screens.exercisescreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.ExerciseDescription
import info.upump.mycompose.models.entity.TypeMuscle
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.exercise.ExerciseChooseVM
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.ExerciseItemCard
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.ListExercise
import info.upump.mycompose.ui.screens.screenscomponents.screen.Chips
import info.upump.mycompose.ui.screens.screenscomponents.screen.ImageByDay
import info.upump.mycompose.ui.screens.screenscomponents.screen.RowChips
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseChooseScreen(
    parentId: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>,
    modifier: Modifier = Modifier
) {
    appBarTitle.value = stringResource(id = R.string.exercise_choose_title_add_exercise)
    val exerciseVM: ExerciseChooseVM = viewModel()
    val listState = rememberLazyListState()
    Scaffold(
    ) { it ->
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxWidth()
            ) {
                ImageByDay(
                    day = exerciseVM.day.collectAsState().value
                )
                //RowChips(Chips)
            }
            ListExercise(
                list = exerciseVM.subItems.collectAsState().value,
                lazyListState = listState,
                navhost = navHostController,
                action = {},
                modifier = Modifier.weight(4.5f)
            )
        }
    }

    LaunchedEffect(key1 = true) {
        exerciseVM.getByParent(parentId)
    }
}

/*@Preview(showBackground = true)
@Composable
fun ExerciseChooseScreenPreview() {
    val id = 1L
    val m: MutableState<String> =
        MutableStateFlow(" ").asStateFlow().collectAsState() as MutableState<String>

    ExerciseChooseScreen(
        parentId = id,
        navHostController = NavHostController(LocalContext.current),
        paddingValues = PaddingValues(20.dp),
        appBarTitle = m
    )
}*/

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun testImageWithChips() {

    val valuesMuscle = TypeMuscle.values()
    val list = mutableListOf<Chips>()
    valuesMuscle.forEach {
       list.add(Chips(title = stringResource(id = it.title), R.drawable.ic_delete_24){})
    }


    Column {
        Box(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxWidth()
        ) {
            ImageByDay(
                day = Day.TUESDAY
            )


            RowChips(
                modifier = Modifier.align(Alignment.BottomCenter),
             *list.toTypedArray()
            )
        }
        val exD = ExerciseDescription()
        exD.title = "smth sporty"

        val navHostController = NavHostController(LocalContext.current)
        val listExer = listOf(
            Exercise().apply { exerciseDescription = exD },
            Exercise().apply { exerciseDescription = exD },
            Exercise().apply { exerciseDescription = exD },
            Exercise().apply { exerciseDescription = exD },
        )
        ListExercise(
            list = listExer,
            lazyListState = LazyListState(),
            navhost = navHostController,
            modifier = Modifier.weight(4.5f)
        ) {}

    }
}
