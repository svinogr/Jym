package info.upump.mycompose.ui.screens.myworkouts

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.defaultscreen.DefaultDetailCycleScreen
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.WorkoutItemCard
import info.upump.mycompose.ui.screens.tabs.TabsItems
import info.upump.mycompose.ui.theme.MyTextLabel12
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "DiscouragedApi")
@OptIn(
    ExperimentalFoundationApi::class, ExperimentalPagerApi::class, ExperimentalTextApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun MyCycleDetailScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val cycleVM: CycleDetailVM = viewModel()
    val cycle by cycleVM.cycle.collectAsState()

    val isLoading = cycleVM.isLoading.collectAsState(true)

    val tabList = listOf(
        TabsItems.TitleCycleTab,
        TabsItems.DescriptionCycleTab
    )
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Log.d("TAG", "${isLoading.value}")

    appBarTitle.value = cycle.title!!

    LaunchedEffect(key1 = true) {
        cycleVM.getDefaultCycleBy(id)
    }

    Column(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
        Box() {
            Image(
                painter = if (isLoading.value) {
                    painterResource(id = R.drawable.logo_upump_round)
                } else {
                    painterResource(getCycleImage(cycle, LocalContext.current))
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
                            Text(
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

        TabsContent(
            tabs = tabList,
            pagerState = pagerState,
            cycle,
            navHostController = navHostController
        )
    }


    if (isLoading.value) {
        CircularProgressIndicator()
    }
}


fun getCycleImage(cycle: Cycle, context: Context): Int {
    val name = context.packageName
    val id: Int

    if (cycle.image == null) {
        if (cycle.defaultImg == null) {
            id = context.resources.getIdentifier("drew", "drawable", name)
        } else {
            id = context.resources.getIdentifier(cycle.defaultImg, "drawable", name)
        }

    } else {
        id = context.resources.getIdentifier(cycle.image, "drawable", name)
    }

    return id
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalPagerApi
@Composable
fun TabsContent(
    tabs: List<TabsItems>,
    pagerState: PagerState,
    cycle: Cycle,
    navHostController: NavHostController
) {
    HorizontalPager(pageCount = tabs.size, state = pagerState, verticalAlignment = Alignment.Top) {
        when (it) {
            0 -> DefaultDetailTitleCycleScreen(cycle, navHostController)
            1 -> DefaultDetailDescriptionCycleScreen(cycle)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDefaultDetailCycleScreen() {
    val nav = NavHostController(LocalContext.current)
    DefaultDetailCycleScreen(1, nav)
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DefaultDetailTitleCycleScreen(cycle: Cycle, navHostController: NavHostController) {
    Log.d("TAG", "DefaultDetailTitleCycleScreen")
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn() {
            cycle.workoutList.forEach {
                item {
                    WorkoutItemCard(workout = it, navHost = navHostController)
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DefaultDetailDescriptionCycleScreen(cycle: Cycle) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Card(modifier = Modifier.fillMaxWidth()) {

                Column(modifier = Modifier.fillMaxWidth()) {
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                        val text = createRef()
                        val gui = createGuidelineFromStart(0.5f)
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            style = MyTextLabel12,
                            text = "Дата начала"
                        )
                        Text(modifier = Modifier.constrainAs(text) {
                            start.linkTo(gui, margin = 8.dp)
                        }, style = MyTextLabel12, text = "Дата окончания")
                    }
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                        val text = createRef()
                        val gui = createGuidelineFromStart(0.5f)
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = cycle.startStringFormatDate
                        )
                        Text(modifier = Modifier.constrainAs(text) {
                            start.linkTo(gui, margin = 8.dp)
                        }, text = cycle.finishStringFormatDate)
                    }
                }
            }
            Card() {
                Text(modifier = Modifier.padding(8.dp), text = "${cycle.comment}")
            }

        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDefaultDetailTitleCycleScreen() {
    val cycle = Cycle(
        title = "ПРограмма", workoutList = listOf(),
        isDefaultType = true, image = "nach1",
        defaultImg = "nach1"
    )

    val list = listOf(
        Workout(
            title = "Новая",
            isWeekEven = false, isDefaultType = false,
            isTemplate = false, day = Day.FRIDAY, exercises = listOf()
        ), Workout(
            title = "Новая1",
            isWeekEven = false, isDefaultType = false,
            isTemplate = false, day = Day.THURSDAY, exercises = listOf()
        ), Workout(
            title = "Новая2",
            isWeekEven = false, isDefaultType = false,
            isTemplate = false, day = Day.MONDAY, exercises = listOf()
        )
    )

    cycle.workoutList = list
    val nav = NavHostController(LocalContext.current)

    DefaultDetailTitleCycleScreen(cycle, nav)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDefaultDetailDescriptionCycleScreen() {
    val cycle = Cycle(
        title = "ПРограмма", workoutList = listOf(),
        isDefaultType = true, image = "uk2",
        defaultImg = "nach1"
    )
    cycle.comment =
        "Lorem ipsum dolor sit amet, consectetuer adipiscing . Aelit" +
                "enean commodo ligula eget dolor. Aenean massa. "
    DefaultDetailDescriptionCycleScreen(cycle)
}

