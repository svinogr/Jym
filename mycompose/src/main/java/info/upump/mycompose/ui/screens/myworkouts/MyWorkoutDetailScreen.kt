package info.upump.mycompose.ui.screens.myworkouts

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.ExerciseDescription
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.workout.WorkoutDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.ExerciseItemCard
import info.upump.mycompose.ui.screens.tabs.TabsItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class, ExperimentalTextApi::class)
@Composable
fun MyWorkoutDetailScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val workoutVM: WorkoutDetailVM = viewModel()
    val workout by workoutVM.workout.collectAsState()
    val exercise by workoutVM.exercises.collectAsState()
    val isLoading = workoutVM.isLoading.collectAsState(true)

    val tabList = listOf(
        TabsItems.TitleCycleTab,
        TabsItems.DescriptionCycleTab
    )
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Log.d("TAG", "${isLoading.value}")

    appBarTitle.value = workout.title!!

    LaunchedEffect(key1 = true) {
        workoutVM.getWorkoutBy(id)
    }

    Column(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
        Box {

            val context = LocalContext.current

            Image(
                bitmap = if (!isLoading.value) {
                    getWorkoutImage(workout, LocalContext.current).asImageBitmap()
                } else {
                    getDefaultImage(context = context).asImageBitmap()
                },

                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            TabRow(modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .fillMaxWidth(),
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.Transparent,
                contentColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                })
            {
                tabList.forEachIndexed { index, tab ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        text = {
                            androidx.compose.material.Text(
                                text = stringResource(id = tab.title), style =
                                TextStyle.Default.copy(
                                    fontSize = 20.sp,
                                    drawStyle = Stroke(
                                        miter = 1f, width = 4f, join = StrokeJoin.Round
                                    )
                                )
                            )
                        },
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(page = index)
                            }
                        },
                    )
                }
            }
        }

        TabsWorkoutContent(
            tabs = tabList,
            pagerState = pagerState,
            workout,
            exercise,
            navHostController = navHostController
        )

        if (isLoading.value) {
            CircularProgressIndicator()
        }
    }
}

fun getWorkoutImage(workout: Workout, context: Context): Bitmap {
    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(context.getColor(workout.day!!.getColor())) //TODO здесь иногда вылетае не понятно почему

    return bitmap
}

fun getDefaultImage(context: Context): Bitmap {
    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(context.getColor(Day.THURSDAY.getColor()))
    return bitmap
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalPagerApi
@Composable
fun TabsWorkoutContent(
    tabs: List<TabsItems>,
    pagerState: PagerState,
    workout: Workout,
    exercises: List<Exercise>,
    navHostController: NavHostController
) {
    HorizontalPager(pageCount = tabs.size, state = pagerState, verticalAlignment = Alignment.Top) {
        Log.d("HorizontalPager", "${workout.exercises}")
        when (it) {
            0 -> DetailTitleWorkoutScreen(workout.exercises, navHostController)
            1 -> DetailDescriptionWorkoutScreen(workout)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDefaultDetailWorkoutScreen() {
    val nav = NavHostController(LocalContext.current)
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>

    MyWorkoutDetailScreen(1, nav, PaddingValues(20.dp), m)
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailTitleWorkoutScreen(exercises: List<Exercise>, navHostController: NavHostController) {
    Log.d("TAG", "DefaultDetailTitleCycleScreen")
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn() {
            exercises.forEach {
                item {
                    ExerciseItemCard(exercise = it, navHost = navHostController)
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailDescriptionWorkoutScreen(workout: Workout) {
    Log.d("DefaultDetailDescriptionCycleScreen", "таг        $workout")
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

            Card() {
                Text(modifier = Modifier.padding(8.dp), text = "${workout.comment}")
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDetailTitleWorkoutScreen() {
    val workout = Workout(
        exercises = listOf(),
        isDefaultType = true
    ).apply { title = "Новая" }

    val exerdescription = ExerciseDescription(img = "nach1", defaultImg = "nach1")
    val listSets = listOf(
        Sets(20.0, 10, 15.0),
        Sets(20.0, 10, 15.0),
        Sets(20.0, 10, 15.0)
    )
    val list = listOf(
        Exercise(
            isDefaultType = false,
            isTemplate = false, exerciseDescription = exerdescription, setsList = listSets
        ).apply { title = "Новое упраж" }, Exercise(
            isDefaultType = false,
            isTemplate = false, exerciseDescription = exerdescription, setsList = listSets
        ).apply { title = "Новое упраж2" }, Exercise(

            isDefaultType = false,
            isTemplate = false, exerciseDescription = exerdescription, setsList = listSets
        ).apply { title = "Новое упраж3" }
    )

    workout.exercises = list
    val nav = NavHostController(LocalContext.current)

    DetailTitleWorkoutScreen(list, nav)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDetailDescriptionWorkoutScreen() {
    val workout = Workout(
        exercises = listOf(),
        isDefaultType = true
    ).apply { title = "Программа" }

    workout.comment =
        "Lorem ipsum dolor sit amet, consectetuer adipiscing . Aelit" +
                "enean commodo ligula eget dolor. Aenean massa. "
    DetailDescriptionWorkoutScreen(workout)
}

