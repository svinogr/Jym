import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.mainscreen.isScrollingUp
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.WorkoutDetailVMInterface
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.workout.WorkoutDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.BottomSheet
import info.upump.mycompose.ui.screens.screenscomponents.FloatExtendedButtonWithState
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.Chips
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DateCard
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.ImageByDay
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.ListExercise
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.RowChips
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.SnackBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AlterWorkoutDetailScreenM3(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val listState = rememberLazyListState()
    val workoutVM: WorkoutDetailVM = viewModel()
    val context = LocalContext.current

    val coroutine = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    /*  val scaffoldState = rememberBottomSheetScaffoldState(
          bottomSheetState = sheetState
      )
  */
    LaunchedEffect(key1 = true) {
        workoutVM.getBy(id)
    }

    if (id == 0L) {
        appBarTitle.value = context.resources.getString(R.string.workout_dialog_create_new)
    } else {
        appBarTitle.value = workoutVM.title.collectAsState().value
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                BottomSheet(text = workoutVM.comment.collectAsState().value)
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(top = 0.dp),
            floatingActionButton = {
                FloatExtendedButtonWithState(
                    stringResource(id = R.string.workout_dialog_create_new),
                    listState.isScrollingUp(), R.drawable.ic_add_black_24dp
                ) {
                }
            },
            snackbarHost = {
                SnackbarHost(

                    snackBarHostState
                ) {
                    SnackBar("удалить?", R.drawable.ic_delete_24) {}
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxHeight()
            ) {
                Box(modifier = Modifier.weight(1.5f)) {
                    ImageByDay(day = workoutVM.day.collectAsState().value)

                    RowChips(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        Chips(
                            stringResource(id = R.string.chips_delete),
                            R.drawable.ic_delete_24,
                        ) {
                            coroutine.launch {
                                snackBarHostState.showSnackbar("")
                            }
                        },
                        Chips(
                            stringResource(id = R.string.to_view_workout),
                            R.drawable.ic_yey
                        ) {
                            coroutine.launch() {
                                bottomState.show()
                            }
                        },
                        Chips(
                            stringResource(id = R.string.chips_comment),
                            R.drawable.ic_info_black_24dp
                        ) {
                            coroutine.launch() {
                                bottomState.show()
                            }
                        },
                        Chips(
                            stringResource(id = R.string.chips_edite),
                            R.drawable.ic_edit_black_24dp,
                        ) {},
                    )
                }

                DateCard(
                    workoutVM.startDate.collectAsState().value,
                    workoutVM.finishDate.collectAsState().value,
                )

                ListExercise(
                    list = workoutVM.subItems.collectAsState().value,
                    listState, navhost = navHostController,
                    Modifier.weight(4f)
                )

            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AlterCycleDetailScreenPreview() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    AlterWorkoutDetailScreenM3(
        0L,
        NavHostController(LocalContext.current),
        PaddingValues(),
        m
    )
}
