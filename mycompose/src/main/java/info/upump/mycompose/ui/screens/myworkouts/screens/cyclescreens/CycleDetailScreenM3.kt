package info.upump.mycompose.ui.screens.myworkouts.screens.cyclescreens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.mainscreen.isScrollingUp
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleDetailVM
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.BottomSheet
import info.upump.mycompose.ui.screens.screenscomponents.FloatExtendedButtonWithState
import info.upump.mycompose.ui.screens.screenscomponents.screen.Chips
import info.upump.mycompose.ui.screens.screenscomponents.screen.CardDate
import info.upump.mycompose.ui.screens.screenscomponents.screen.ImageForDetailScreen
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.ListWorkouts
import info.upump.mycompose.ui.screens.screenscomponents.screen.RowChips
import info.upump.mycompose.ui.screens.screenscomponents.screen.SnackBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AlterCycleDetailScreenM3(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val cycleVM: CycleDetailVM = viewModel()
    val listState = rememberLazyListState()

    val coroutine = rememberCoroutineScope()

    appBarTitle.value = cycleVM.title.collectAsState().value

    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val snackBarHostState = remember { SnackbarHostState() }

    val list by remember {
        mutableStateOf(cycleVM.subItems)
    }

    LaunchedEffect(key1 = true) {
        Log.d("efect", "efect")
        cycleVM.getBy(id)
    }

    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                BottomSheet(text = cycleVM.comment.collectAsState().value)
            }
        }
    ) {
        Scaffold(modifier = Modifier.padding(top = 0.dp),
            floatingActionButton = {
                FloatExtendedButtonWithState(
                    stringResource(id = R.string.workout_dialog_create_new),
                    listState.isScrollingUp(), R.drawable.ic_add_black_24dp
                ) {
                    navHostController.navigate(
                        NavigationItem.CreateWorkoutNavigationItem.routeWith(
                            id
                        )
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(
                    snackBarHostState
                ) {
                    SnackBar(stringResource(id = R.string.clean_cycle), R.drawable.ic_delete_24) {
                        cycleVM.cleanItem()
                    }
                }
            }

        ) {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxHeight()
            ) {
                Box(modifier = Modifier.weight(1.5f)) {
                    ImageForDetailScreen(
                        image = cycleVM.img.collectAsState().value,
                        defaultImage = cycleVM.imgDefault.collectAsState().value,
                    )

                    RowChips(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        Chips(
                            stringResource(id = R.string.chips_clear),
                            R.drawable.ic_delete_24,
                        ) {
                            coroutine.launch {
                                snackBarHostState.showSnackbar("")
                            }
                        },
                        Chips(
                            stringResource(id = R.string.chips_edite),
                            R.drawable.ic_edit_black_24dp,
                        ) {
                            navHostController.navigate(
                                NavigationItem.CreateEditeCycleNavigationItem.routeWith(
                                    id
                                )
                            )
                        },
                        Chips(
                            stringResource(id = R.string.chips_comment),
                            R.drawable.ic_info_black_24dp
                        ) {
                            coroutine.launch() {
                                bottomState.show()
                            }
                        }
                    )
                }

                CardDate(
                    cycleVM.startDate.collectAsState().value,
                    cycleVM.finishDate.collectAsState().value,
                    false
                )

                val del: (Long) -> Unit = { cycleVM.deleteSubItem(it) }
                ListWorkouts(
                    list = list.collectAsState().value,
                    listState, navhost = navHostController,
                    Modifier.weight(4f),

                    ) { id ->
                    del(id)
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AlterCycleDetailScreenPreview() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    AlterCycleDetailScreenM3(
        0L, NavHostController(LocalContext.current),
        PaddingValues(),
        m
    )
}