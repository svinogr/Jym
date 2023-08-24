package info.upump.mycompose.ui.screens.myworkouts.cyclescreens

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.defaultscreen.DefaultDetailCycleScreen
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.VMDetailInterface
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleDetailVM
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.FloatActionButtonWithState
import info.upump.mycompose.ui.screens.screenscomponents.WorkoutItemCard
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DateCard
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DescriptionCard
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.ImageForDetailScreen
import info.upump.mycompose.ui.screens.tabs.TabsItems
import info.upump.mycompose.ui.theme.MyOutlineTextTitleLabel20Text
import kotlinx.coroutines.launch

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "DiscouragedApi",
    "UnrememberedMutableState"
)
@OptIn(
    ExperimentalFoundationApi::class, ExperimentalPagerApi::class, ExperimentalTextApi::class,
    ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun MyCycleDetailScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val cycleDetailVM: CycleDetailVM = viewModel()

    val isLoading = cycleDetailVM.isLoading.collectAsState(true)
    val title by cycleDetailVM.title.collectAsState()

    val tabList = listOf(
        TabsItems.TitleCycleTab,
        TabsItems.DescriptionCycleTab
    )

    appBarTitle.value = title

    val pagerState = rememberPagerState()


    val iconState = remember {
        mutableStateOf(R.drawable.ic_add_black_24dp)
    }

    val listState = rememberLazyListState()

    val actionRoutState = remember {
        mutableStateOf(
            NavigationItem.CreateEditeCycleNavigationItem.routeWith(0)
        )
    }

    Scaffold(modifier = Modifier.padding(top = 0.dp),
        floatingActionButton = {
            FloatActionButtonWithState(listState.isScrollingUp(), iconState.value) {
                navHostController.navigate(
                    actionRoutState.value
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            Box() {
                ImageForDetailScreen(cycleDetailVM)
                TabRow(modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .fillMaxWidth(),
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.Transparent,
                    indicator = { list ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(list[pagerState.currentPage])
                        )
                    }
                )
                {
                    val scope = rememberCoroutineScope()
                    tabList.forEachIndexed { index, tab ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            text = {
                                Text(
                                    text = stringResource(id = tab.title), style =
                                    MyOutlineTextTitleLabel20Text
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
                cycleDetailVM,
                navHostController = navHostController,
                iconState,
                actionRoutState
            )
        }
    }

    LaunchedEffect(key1 = true) {
        cycleDetailVM.getInitialItem(id)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@ExperimentalPagerApi
@Composable
fun TabsContent(
    tabs: List<TabsItems>,
    pagerState: PagerState,
    cycleVM: VMDetailInterface<Cycle, Workout>,
    navHostController: NavHostController,
    fabIcons: MutableState<Int>,
    actionRoutState: MutableState<String>
) {
    val id = cycleVM.id.collectAsState()

    if (pagerState.currentPage == 0) {
        // создание новой тренировки
        actionRoutState.value = NavigationItem.CreateWorkoutNavigationItem.routeWith(id.value)
        fabIcons.value = R.drawable.ic_add_black_24dp
    } else {
        // редакт программы
        actionRoutState.value = NavigationItem.CreateEditeCycleNavigationItem.routeWith(id.value)
        fabIcons.value = R.drawable.ic_edit_black_24dp
    }

    HorizontalPager(pageCount = tabs.size, state = pagerState, verticalAlignment = Alignment.Top) {
        when (it) {
            0 -> DefaultDetailTitleCycleScreen(cycleVM, navHostController)
            1 -> DefaultDetailDescriptionCycleScreen(cycleVM)
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DefaultDetailTitleCycleScreen(
    cycleVM: VMDetailInterface<Cycle, Workout>,
    navHostController: NavHostController,
) {
    val lazyState = rememberLazyListState()
    val workoutList = cycleVM.subItems.collectAsState().value
    LazyColumn(
        state = lazyState,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(id = R.color.colorBackgroundConstrateLayout))
    ) {
        workoutList.forEach {
            item {
                WorkoutItemCard(workout = it, navHost = navHostController)
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DefaultDetailDescriptionCycleScreen(
    cycleVM: VMDetailInterface<Cycle, Workout>
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(color = colorResource(id = R.color.colorBackgroundConstrateLayout))
    ) {
        DateCard(
            cycleVM.startDate.collectAsState().value,
            cycleVM.finishDate.collectAsState().value
        )
        DescriptionCard(cycleVM.comment.collectAsState().value)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDefaultDetailCycleScreen() {
    val nav = NavHostController(LocalContext.current)
    DefaultDetailCycleScreen(1, nav)
}


@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDefaultDetailTitleCycleScreen() {
    val nav = NavHostController(LocalContext.current)
    DefaultDetailTitleCycleScreen(CycleDetailVM.vmOnlyForPreview, nav)
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDefaultDetailDescriptionCycleScreen() {
    DefaultDetailDescriptionCycleScreen(CycleDetailVM.vmOnlyForPreview)
}

