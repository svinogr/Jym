package info.upump.mycompose.ui.screens.defaultscreen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.VMDetailInterface
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.WorkoutItemCard
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DateCard
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DescriptionCard
import info.upump.mycompose.ui.screens.tabs.TabsItems
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "DiscouragedApi")
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class, ExperimentalTextApi::class)
@Composable
fun DefaultDetailCycleScreen(
    id: Long,
    navHostController: NavHostController
) {
    val cycleVM: CycleDetailVM = viewModel()
    //val cycle by cycleVM.cycle.collectAsState()

    val isLoading = cycleVM.isLoading.collectAsState(true)

    val tabList = listOf(
        TabsItems.TitleCycleTab,
        TabsItems.DescriptionCycleTab
    )
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Log.d("TAG", "${isLoading.value}")

    LaunchedEffect(key1 = true) {
        cycleVM.getInitialItem(id)
    }

    Column {
        Box() {
            val identificatorImg = remember {
                mutableStateOf<Int>(R.drawable.logo_upump_round)
            }
            if (!isLoading.value) {
         //       identificatorImg.value = getImage(cycle, LocalContext.current)
            }

            Image(
                painter = painterResource(id = identificatorImg.value),
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
                contentColor = Color.White,
                containerColor = Color.Transparent,
                indicator = {
                    androidx.compose.material.TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(it[pagerState.currentPage])
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
            cycleVM,
            navHostController = navHostController
        )
    }

    if (isLoading.value) {
        CircularProgressIndicator()
    }
}

fun getImage(cycle: Cycle, context: Context): Int {
    val name = context.packageName
    return context.resources.getIdentifier(cycle.imageDefault, "drawable", name)
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalPagerApi
@Composable
fun TabsContent(
    tabs: List<TabsItems>,
    pagerState: PagerState,
    cycle: VMDetailInterface<Cycle, Workout>,
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
fun DefaultDetailTitleCycleScreen(cycle: VMDetailInterface<Cycle, Workout>, navHostController: NavHostController) {
    Log.d("TAG", "DefaultDetailTitleCycleScreen")
    val workoutList = cycle.subItems.collectAsState().value
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn() {
            workoutList.forEach {
                item {
                    WorkoutItemCard(workout = it, navHost = navHostController)
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DefaultDetailDescriptionCycleScreen(cycleVM: VMDetailInterface<Cycle, Workout>) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(color = colorResource(id = R.color.colorBackgroundConstrateLayout))
    ) {
        DateCard(
            cycleVM.startDate.collectAsState().value,
            cycleVM.finishDate.collectAsState().value
        )
        DescriptionCard(cycleVM.title.collectAsState().value)
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDefaultDetailTitleCycleScreen() {
    val cycle = Cycle(
        workoutList = listOf(),
        isDefaultType = true,
        imageDefault = "nach1"
    )
    cycle.title = "ПРограмма"
    cycle.image = "nach1"

    val list = listOf(
        Workout(
            isWeekEven = false, isDefaultType = false,
            isTemplate = false, day = Day.FRIDAY, exercises = listOf()
        ).apply { title = "Новая" }, Workout(
            isWeekEven = false, isDefaultType = false,
            isTemplate = false, day = Day.THURSDAY, exercises = listOf()
        ).apply { title = "Новая1" }, Workout(

            isWeekEven = false, isDefaultType = false,
            isTemplate = false, day = Day.MONDAY, exercises = listOf()
        ).apply { title = "Новая2" }
    )

    cycle.workoutList = list
    val nav = NavHostController(LocalContext.current)

    DefaultDetailTitleCycleScreen(CycleDetailVM.vmOnlyForPreview, nav)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDefaultDetailDescriptionCycleScreen() {
    val cycle = Cycle(
        workoutList = listOf(),
        isDefaultType = true,
        imageDefault = "nach1"
    ).apply { title = "Новая" }
    cycle.image = "nach1"
    cycle.comment =
        "Lorem ipsum dolor sit amet, consectetuer adipiscing . Aelit" +
                "enean commodo ligula eget dolor. Aenean massa. "
    DefaultDetailDescriptionCycleScreen(CycleDetailVM.vmOnlyForPreview)
}
