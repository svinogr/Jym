package info.upump.mycompose.ui.screens.defaultscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleDetailVM
import info.upump.mycompose.ui.screens.tabs.TabsItems
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun DefaultDetailCycleScreen(
    id: Long
) {
    val cycleVM: CycleDetailVM = viewModel()
    val cycle by cycleVM.cycle.collectAsState()

    val tabList = listOf(
        TabsItems.TitleCycleTab,
        TabsItems.DescriptionCycleTab
    )
    val pagerState = rememberPagerState(initialPage = tabList.size)
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
            tabList.forEachIndexed { index, tab ->
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
        TabsContent(tabs = tabList, pagerState = pagerState, cycle)
    }
    cycleVM.getDefaultCycleBy(id)
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalPagerApi
@Composable
fun TabsContent(tabs: List<TabsItems>, pagerState: PagerState, cycle: Cycle) {
    HorizontalPager(pageCount = tabs.size, state = pagerState) {
        if (pagerState.currentPage == 0) DefaultDetailTitleCycleScreen(cycle)
        else DefaultDetailDescriptionCycleScreen(cycle)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PreviewDefaultDetailCycleScreen() {

    DefaultDetailCycleScreen(1)
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DefaultDetailTitleCycleScreen(cycle: Cycle) {
    Log.d("TAG", "DefaultDetailTitleCycleScreen")
    Column(Modifier.padding(top = 80.dp)) {

        Text(text = "${cycle.title}")
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DefaultDetailDescriptionCycleScreen(cycle: Cycle) {
    Log.d("TAG", "DefaultDetailDescriptionCycleScreen")
    Log.d("DefaultDetailDescriptionCycleScreen", "таг        $cycle")
    Column(Modifier.padding(top = 80.dp)) {

        Text(text = "${cycle.comment}")
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDefaultDetailTitleCycleScreen() {
    //DefaultDetailTitleCycleScreen(id = 1)
}

@Preview(showBackground = true)
@Composable
fun PreviewDefaultDetailDescriptionCycleScreen() {
   // DefaultDetailTitleCycleScreen(id = 1)
}
