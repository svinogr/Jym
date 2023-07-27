package info.upump.mycompose.ui.screens.myworkouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.SetsVM
import info.upump.mycompose.ui.screens.screenscomponents.SetsItemCard
import info.upump.mycompose.ui.theme.MyTextLabel16
import info.upump.mycompose.ui.theme.MyTextTitleLabel16
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun MyExerciseDetailScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val setsVm: SetsVM = viewModel()
    val load = setsVm.isLoading.collectAsState()
    val sets = setsVm.sets.collectAsState()
    val modifier = Modifier.fillMaxWidth()
    appBarTitle.value = stringResource(id = R.string.exercise_title_sets)

    Column(modifier = modifier.padding(top = paddingValues.calculateTopPadding())) {
        ConstraintLayout(modifier = modifier) {
            val textNumber = createRef()
            val textWeight = createRef()
            val textPasWeight = createRef()
            val textReps = createRef()
            val guiOne = createGuidelineFromStart(0.02f)
            val guiTwo = createGuidelineFromStart(0.20f)
            val guiThree = createGuidelineFromStart(0.40f)
            val guiFour = createGuidelineFromStart(0.72f)
            val modifierOneThree = Modifier.padding(start = 2.dp)
            androidx.compose.material.Text(
                text = "№",
                modifier = modifierOneThree.constrainAs(textNumber) {
                    start.linkTo(guiOne)
                }, style = MyTextTitleLabel16
            )

            androidx.compose.material.Text(
                text = stringResource(id = R.string.label_weight_set),
                modifier = modifierOneThree.constrainAs(textWeight) {
                    start.linkTo(guiTwo)
                }, style = MyTextTitleLabel16
            )

            androidx.compose.material.Text(
                text = stringResource(id = R.string.label_weight_set_past),
                modifier = modifierOneThree.constrainAs(textPasWeight) {
                    start.linkTo(guiThree)
                }, style = MyTextLabel16
            )

            androidx.compose.material.Text(
                text = stringResource(id = R.string.label_reps_sets_short),
                modifier = modifierOneThree.constrainAs(textReps) {
                    start.linkTo(guiFour)
                }, style = MyTextTitleLabel16
            )
        }
        LazyColumn() {
            itemsIndexed(sets.value) { index, item ->
                SetsItemCard(sets = item, navHost = navHostController, number = index + 1)
            }
        }
    }

    LaunchedEffect(key1 = true) {
        setsVm.getSets(id)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMyExerciseDetailScreen() {
    val id = 1L
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>

    MyExerciseDetailScreen(
        id = id,
        navHostController = NavHostController(LocalContext.current),
        paddingValues = PaddingValues(20.dp),
        appBarTitle = m
    )
}
