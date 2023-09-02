package info.upump.mycompose.ui.screens.myworkouts.exercisescreens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.mainscreen.isScrollingUp
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.exercise.ExerciseVM
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.FloatExtendedButtonWithState
import info.upump.mycompose.ui.screens.screenscomponents.screen.ListSets
import info.upump.mycompose.ui.screens.screenscomponents.screen.TableHeader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyExerciseDetailScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val exerciseVM: ExerciseVM = viewModel()
    val load = exerciseVM.isLoading.collectAsState()
    val lazyListState = LazyListState()
    appBarTitle.value = stringResource(id = R.string.exercise_title_sets)
    Log.d("saveItem", "$id")

    LaunchedEffect(key1 = true){
            exerciseVM.getBy(id)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingValues.calculateTopPadding()),
        floatingActionButton = {
            FloatExtendedButtonWithState(text = stringResource(id = R.string.set_create_),
                isVisible = lazyListState.isScrollingUp(),
                icon = R.drawable.ic_add_black_24dp ) {
                navHostController.navigate(NavigationItem.CreateSetsNavigationItem.routeWithId(id))
            }
        }
    ) { it ->
        Column(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            TableHeader()
            ListSets(
                navHost = navHostController,
                list = exerciseVM.subItems.collectAsState().value,
                listState = lazyListState
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMyExerciseDetailScreen() {
    val id = 1L
    val m: MutableState<String> =
        MutableStateFlow(" ").asStateFlow().collectAsState() as MutableState<String>

    MyExerciseDetailScreen(
        id = id,
        navHostController = NavHostController(LocalContext.current),
        paddingValues = PaddingValues(20.dp),
        appBarTitle = m
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMyExerciseDetailScreen2() {
    val id = 1L
    val m: MutableState<String> =
        MutableStateFlow(" ").asStateFlow().collectAsState() as MutableState<String>
    Column(modifier = Modifier
        .padding(top = 20.dp)
        .fillMaxWidth()) {
        TableHeader()
        ListSets(
            navHost = NavHostController(LocalContext.current),
            list = ExerciseVM.vmOnlyForPreview.subItems.collectAsState().value,
            listState = LazyListState()
        )
    }
}


