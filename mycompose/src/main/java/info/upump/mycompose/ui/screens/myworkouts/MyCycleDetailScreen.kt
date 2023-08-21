package info.upump.mycompose.ui.screens.myworkouts

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.defaultscreen.DefaultDetailCycleScreen
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.WorkoutItemCard
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DateCard
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DescriptionCard
import info.upump.mycompose.ui.screens.tabs.TabsItems
import info.upump.mycompose.ui.theme.MyOutlineTextTitleLabel20Text
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "DiscouragedApi")
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
    val cycleVM: CycleDetailVM = viewModel()
    val cycle by cycleVM.cycle.collectAsState()
    val workout by cycleVM.workout.collectAsState()

    val isLoading = cycleVM.isLoading.collectAsState(true)

    val tabList = listOf(
        TabsItems.TitleCycleTab,
        TabsItems.DescriptionCycleTab
    )
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Log.d("TAG", "${isLoading.value}")

    appBarTitle.value = cycle.title!!
    val density = LocalDensity.current
    val visibleFab = remember {
        mutableStateOf(true) // не забыть псотавить фолс для начального
    }

    val image = cycle.image
    LaunchedEffect(key1 = true) {
        cycleVM.getDefaultCycleBy(id)
    }

    val fabIcons = remember {
        mutableStateOf(R.drawable.ic_add_black_24dp)
    }

    Scaffold(modifier = Modifier.padding(top = 0.dp),
        floatingActionButton = {
            AnimatedVisibility(
                visible = visibleFab.value,
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { 100.dp.roundToPx() }
                },
                exit = slideOutVertically {
                    with(density) { 100.dp.roundToPx() }
                }
            ) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        /*  cycleVMCreateEdit.save() {
                            navHostController.navigate(
                                NavigationItem.DetailCycleNavigationItem.routeWithId(it)
                            )
                        }*/
                    },
                    content = {
                        Icon(
                            painter = painterResource(id = fabIcons.value),
                            contentDescription = "next"
                        )
                    }
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            Box() {
                Image(
                    bitmap = getCycleImage(image, LocalContext.current).asImageBitmap(),
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
                    containerColor = Color.Transparent,
                    indicator = {list ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(list[pagerState.currentPage])
                        )
                    }
                )
                {
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
                cycle,
                workout,
                navHostController = navHostController,
                fabIcons,
                visibleFab
            )
        }
    }
}


fun getCycleImage(image: String, context: Context): Bitmap {
    var bitmap: Bitmap
    val name = context.packageName

    var source: ImageDecoder.Source
    try {
        if (!image.isBlank()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                source = ImageDecoder.createSource(context.contentResolver, Uri.parse(image))
                bitmap = ImageDecoder.decodeBitmap(source)
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    Uri.parse(image)
                );
            }

        } else {
            val id = context.resources.getIdentifier("drew", "drawable", name)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                source = ImageDecoder.createSource(context.resources, id)
                bitmap = ImageDecoder.decodeBitmap(source)
            } else {
                bitmap = BitmapFactory.decodeResource(context.resources, id);
            }
        }
    } catch (e: NullPointerException) {
        val id = context.resources.getIdentifier("drew", "drawable", name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            source = ImageDecoder.createSource(context.resources, id)
            bitmap = ImageDecoder.decodeBitmap(source)
        } else {
            bitmap = BitmapFactory.decodeResource(context.resources, id);
        }
    }
    return bitmap
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalPagerApi
@Composable
fun TabsContent(
    tabs: List<TabsItems>,
    pagerState: PagerState,
    cycle: Cycle,
    workout: List<Workout>,
    navHostController: NavHostController,
    fabIcons: MutableState<Int>,
    visibleFab: MutableState<Boolean>
) {
    if (pagerState.currentPage == 0) fabIcons.value = R.drawable.ic_add_black_24dp else {
        fabIcons.value = R.drawable.ic_edit_black_24dp
    }
    Log.d("pager", "${pagerState.currentPage}")
    HorizontalPager(pageCount = tabs.size, state = pagerState, verticalAlignment = Alignment.Top) {
        when (it) {
            0 -> DefaultDetailTitleCycleScreen(workout, navHostController, visibleFab)
            1 -> DefaultDetailDescriptionCycleScreen(cycle)
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DefaultDetailTitleCycleScreen(
    workoutList: List<Workout>,
    navHostController: NavHostController,
    visibleFab: MutableState<Boolean>
) {
    val lazyState = rememberLazyListState()
    visibleFab.value = lazyState.isScrollingUp()
    LazyColumn(state = lazyState,
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
            . background(colorResource(id = R.color.colorBackgroundConstrateLayout))) {
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
    cycle: Cycle
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(color = colorResource(id = R.color.colorBackgroundConstrateLayout))
    ) {
        DateCard(cycle)
        DescriptionCard(entity = cycle)
    }
}

@OptIn(ExperimentalFoundationApi::class)
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
    val cycle = Cycle(
        workoutList = listOf(),
        isDefaultType = true,
        defaultImg = "nach1"
    ).apply { title = "Новая" }
    cycle.image = "nach1"
    val list = listOf(
        Workout(
            isWeekEven = false, isDefaultType = false,
            isTemplate = false, day = Day.FRIDAY, exercises = listOf()
        ).apply { title = "Новая" }, Workout(
            isWeekEven = false, isDefaultType = false,
            isTemplate = false, day = Day.THURSDAY, exercises = listOf()
        ).apply { title = "Новая2" }, Workout(
            isWeekEven = false, isDefaultType = false,
            isTemplate = false, day = Day.MONDAY, exercises = listOf()
        ).apply { title = "Новая3" }
    )

    cycle.workoutList = list
    val nav = NavHostController(LocalContext.current)

    DefaultDetailTitleCycleScreen(list, nav, mutableStateOf(true))
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDefaultDetailDescriptionCycleScreen() {
    val cycle = Cycle(
        workoutList = listOf(),
        isDefaultType = true,
        defaultImg = "nach1"
    ).apply { title = "Новая" }
    cycle.image = "nach1"
    cycle.comment =
        "Lorem ipsum dolor sit amet, consectetuer adipiscing . Aelit" +
                "enean commodo ligula eget dolor. Aenean massa. "
    DefaultDetailDescriptionCycleScreen(cycle)
}

