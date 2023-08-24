package info.upump.mycompose.ui.screens.myworkouts

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.myworkouts.cyclescreens.CreateEditeCycleScreen
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.workout.WorkoutVM
import info.upump.mycompose.ui.screens.screenscomponents.FloatActionButtonWithState
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DayCard
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DescriptionCardWithVM
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.ImageByDay
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.ImageTitleImageTitle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateWorkoutScreen(
    id: Long,
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
       // .padding(top = it.calculateTopPadding())
        .verticalScroll(rememberScrollState())
        .background(color = colorResource(id = R.color.colorBackgroundConstrateLayout))
    LaunchedEffect(key1 = true) {

        workoutVM.getBy(id)
        appBarTitle.value = context.resources.getString(R.string.workout_dialog_create_new)
    }

    Scaffold(
        floatingActionButton = {
            FloatActionButtonWithState(isVisible = true, icon = R.drawable.ic_fab_next) {
                // сохраняем новую и возвращаемся обратно
                if (!workoutVM.isBlankFields()) {
                    workoutVM.saveWith(parentId) {
                        navHostController.popBackStack()//  NavigationItem.DetailWorkoutNavigationItem.routeWithId(it)
                        navHostController.navigateUp()
                    }
                }
            }
        }) {
        Column(
            modifier = columnModifier,
        ) {
            // of thee parts
            ImageTitleImageTitle(workoutVM) {
                ImageByDay(modelVM = workoutVM)
            }
            DayCard(workoutVM)
            // DateCardWithDatePicker(workoutVM)
            // description aka comment
            DescriptionCardWithVM(workoutVM)
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
    CreateEditeCycleScreen(
        id = 1L,
        navHostController = NavHostController(LocalContext.current),
        PaddingValues(20.dp),
        m,
        ActionState.CREATE
    )
}



