package info.upump.mycompose.ui.screens.defaultscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Card
import androidx.compose.material.LeadingIconTab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.WorkoutItemCard
import info.upump.mycompose.ui.screens.tabs.TabsItems
import info.upump.mycompose.ui.theme.MyTextLabel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun DefaultDetailCycleScreen(
    id: Long,
    navHostController: NavHostController
) {
    val cycleVM: CycleDetailVM = viewModel()
    val cycle by cycleVM.cycle.collectAsState()

    val tabList = listOf(
        TabsItems.TitleCycleTab,
        TabsItems.DescriptionCycleTab
    )
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()
    Log.d("DefaultDetailDescriptionCycleScreen", "таг        $cycle")
    Column() {
        Image(
            painter = painterResource(id = R.drawable.my_cycle),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = colorResource(
                id = R.color.colorPrimary,
            ),
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )

            }) {
            Log.d("currentPage", "${tabList.size}")
            tabList.forEachIndexed { index, tab ->
                Log.d("currentPage", "равно  ${pagerState.currentPage == 2}")
                Log.d("currentPage", "текущ ${pagerState.currentPage}")
                Log.d("currentPage", "index = ${index}")
                LeadingIconTab(
                    selected = pagerState.currentPage == index,
                    icon = {},
                    text = {
                        Text(text = stringResource(id = tab.title))
                    },
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }

        TabsContent(
            tabs = tabList,
            pagerState = pagerState,
            cycle,
            navHostController = navHostController
        )
        Log.d("currentPage", "init = ${pagerState.initialPage}")

    }
    cycleVM.getDefaultCycleBy(id)
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
    HorizontalPager(pageCount = tabs.size, state = pagerState) {
        when (pagerState.currentPage) {
            0 -> DefaultDetailTitleCycleScreen(cycle, navHostController)
            1 -> DefaultDetailDescriptionCycleScreen(cycle)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
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
    Log.d("DefaultDetailDescriptionCycleScreen", "таг        $cycle")
    Box(modifier = Modifier.fillMaxWidth()) {
        Column() {
            Card(modifier = Modifier.fillMaxWidth()) {

                Column(modifier = Modifier.fillMaxWidth()) {
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                        val text = createRef()
                        val gui = createGuidelineFromStart(0.5f)
                        Text(modifier = Modifier.padding(start = 8.dp), style = MyTextLabel, text = "Дата начала")
                        Text(modifier = Modifier.constrainAs(text) {
                            start.linkTo(gui, margin = 8.dp)
                        }, style = MyTextLabel, text = "Дата окончания")
                    }
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                        val text = createRef()
                        val gui = createGuidelineFromStart(0.5f)
                        Text(modifier = Modifier.padding(start = 8.dp), text = "11111111111")
                        Text(modifier = Modifier.constrainAs(text) {
                            start.linkTo(gui, margin = 8.dp)
                        }, text = "22222")
                    }
                }
            }
            Card() {
                Text(modifier = Modifier.padding(8.dp), text = "${cycle.comment}")
            }
        }
    }

}


@Preview(showBackground = true)
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

@Preview(showBackground = true)
@Composable
fun PreviewDefaultDetailDescriptionCycleScreen() {
    val cycle = Cycle(
        title = "ПРограмма", workoutList = listOf(),
        isDefaultType = true, image = "nach1",
        defaultImg = "nach1"
    )
    cycle.comment =
        "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget"

    DefaultDetailDescriptionCycleScreen(cycle)
}
