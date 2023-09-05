package info.upump.mycompose.ui.screens.myworkouts.screens.workoutscreens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.workout.WorkoutVM
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.FloatExtendedButtonWithState
import info.upump.mycompose.ui.screens.screenscomponents.screen.CardTitle
import info.upump.mycompose.ui.screens.screenscomponents.screen.DateCardWithDatePicker
import info.upump.mycompose.ui.screens.screenscomponents.screen.DayCardWorkoutEdit
import info.upump.mycompose.ui.screens.screenscomponents.screen.DescriptionCardWithEdit
import info.upump.mycompose.ui.screens.screenscomponents.screen.ImageByDay
import info.upump.mycompose.ui.screens.screenscomponents.screen.LabelTitleForImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateWorkoutScreen(
    parentId: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>,
) {
    val workoutVM: WorkoutVM = viewModel()
    val isLoad by workoutVM.isLoading.collectAsState()

    val context = LocalContext.current
    val columnModifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .background(color = colorResource(id = R.color.colorBackgroundConstrateLayout))

    appBarTitle.value = context.resources.getString(R.string.workout_dialog_create_new)

    LaunchedEffect(key1 = true) {
        workoutVM.getBy(0)

    }

    Scaffold(
        modifier = Modifier.padding(),
        floatingActionButton = {
            FloatExtendedButtonWithState(
                stringResource(id = R.string.picker_dialog_btn_save),
                true, R.drawable.ic_save_black
            ) {
                if (!workoutVM.isBlankFields()) {
                    workoutVM.saveWith(parentId) {
                        navHostController.navigate(
                            NavigationItem.DetailWorkoutNavigationItem.routeWithId(
                                it
                            )
                        )
                    }
                }
            }

        }) {
        Column(
            modifier = columnModifier,
        ) {
            Box(
                modifier = Modifier.weight(1.5f)

            ) {
                ImageByDay(day = workoutVM.day.collectAsState().value)
                LabelTitleForImage(
                    title = workoutVM.title.collectAsState().value, modifier = Modifier.align(
                        Alignment.BottomStart
                    )
                )
            }

            DayCardWorkoutEdit(
                workoutVM.day.collectAsState().value,
                workoutVM::updateDay,
                workoutVM.isEven.collectAsState().value,
                workoutVM::updateEven,
                LocalFocusManager.current,
                Modifier
            )

            DateCardWithDatePicker(
                workoutVM.startDate.collectAsState().value,
                workoutVM::updateStartDate,
                workoutVM.finishDate.collectAsState().value,
                workoutVM::updateFinishDate
            )

            CardTitle(
                text = workoutVM.title.collectAsState().value,
                isError = workoutVM.isTitleError.collectAsState().value,
                updateText = workoutVM::updateTitle,
                modifier = Modifier.weight(1F)
            )

            DescriptionCardWithEdit(
                workoutVM.comment.collectAsState().value,
                workoutVM::updateComment,
                Modifier.weight(3F)
            )
        }
    }
    LaunchedEffect(key1 = true) {
        workoutVM.getBy(0)
    }

    BackHandler {
        navHostController.navigateUp()
    }
}

/*@RequiresApi(Build.VERSION_CODES.P)*/
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCreateEditeWorkoutScreen() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    CreateWorkoutScreen(
        navHostController = NavHostController(LocalContext.current),
        parentId = 1L,
        paddingValues = PaddingValues(20.dp),
        appBarTitle = m
    )
}



