package info.upump.mycompose.ui.screens.myworkoutsscreens.screens.workoutscreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.ExerciseDescription
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.models.entity.TypeMuscle
import info.upump.mycompose.ui.screens.myworkoutsscreens.viewmodel.workout.WorkoutDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.BottomSheet
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.ListWorkoutForReview
import info.upump.mycompose.ui.screens.screenscomponents.screen.Chips
import info.upump.mycompose.ui.screens.screenscomponents.screen.ImageByDay
import info.upump.mycompose.ui.screens.screenscomponents.screen.RowChips
import info.upump.mycompose.ui.screens.screenscomponents.screen.StopWatch
import info.upump.mycompose.ui.screens.screenscomponents.screen.StopWatchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WorkoutReview(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val snackBarHostState = remember { SnackbarHostState() }

    val workoutVM: WorkoutDetailVM = viewModel()
    val exersise by remember {
        mutableStateOf(workoutVM.subItems)
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

            }
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(top = 0.dp)

        ) { it ->
            Column {
                ImageByDay(day = workoutVM.day.collectAsState().value)
                ListWorkoutForReview(exersise.collectAsState().value)
            }


        }
    }

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WorkoutReviewPreview() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val snackBarHostState = remember { SnackbarHostState() }

    val list = listOf(
        Exercise().apply {
            id = 1
            title = "First"
            typeMuscle = TypeMuscle.ABS
            isDefaultType = true
            isTemplate = true
            setsList = mutableListOf(
                Sets(), Sets(), Sets(), Sets(), Sets(), Sets()
            )
            descriptionId = 1
            exerciseDescription = ExerciseDescription().apply {
                img = "nach1"
                defaultImg = "nach1"
                title = "Новое упраж"
                id = descriptionId
            }
        },
        Exercise().apply {
            id = 2
            title = "Second"
            typeMuscle = TypeMuscle.BACK
            isDefaultType = true
            isTemplate = true
            setsList = mutableListOf(
                Sets(), Sets(), Sets()
            )
            descriptionId = 2
            exerciseDescription = ExerciseDescription().apply {
                img = "nach1"
                defaultImg = "nach1"
                title = "Новое упраж"
                id = descriptionId
            }
        },
        Exercise().apply {
            id = 3
            title = "Thead"
            typeMuscle = TypeMuscle.CALVES
            isDefaultType = true
            isTemplate = true
            setsList = mutableListOf(
                Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets()
            )
            descriptionId = 3
            exerciseDescription = ExerciseDescription().apply {
                img = "nach1"
                defaultImg = "nach1"
                title = "Новое упраж"
                id = descriptionId
            }
        }
    )

    val workout = WorkoutDetailVM.vmOnlyForPreview.item.collectAsState()
    val coroutine = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
              BottomSheet(text = workout.value.comment)
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(top = 0.dp)

        ) { it ->
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())

            ) {
                Box(modifier = Modifier.weight(1.5f)) {
                    ImageByDay(day = workout.value.day)
                    RowChips(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        Chips(
                            stringResource(id = R.string.chips_comment),
                            R.drawable.ic_info_black_24dp,
                        ) {
                            coroutine.launch() {
                                bottomState.show()
                            }
                        }
                    )
                }
                ListWorkoutForReview(list, modifier = Modifier.weight(4f))
                Divider(thickness = 1.dp, modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))
                StopWatch(hour = 10, minute = 10, second = 20, stopwatchState = StopWatchState.RESUME)
            }
        }
    }

}
