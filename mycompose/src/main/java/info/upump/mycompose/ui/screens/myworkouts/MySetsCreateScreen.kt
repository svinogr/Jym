/*
package info.upump.mycompose.ui.screens.myworkouts

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.sets.SetsVM
import info.upump.mycompose.ui.screens.screenscomponents.NumberPicker
import info.upump.mycompose.ui.screens.screenscomponents.NumberPickerWithStep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MySetsCreateScreen(
    parentId: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val setVM: SetsVM = viewModel()
    val set by setVM.sets.collectAsState()
    val isLoad = setVM.isLoading.collectAsState()
    val newSets = Sets()
    newSets.parentId = parentId
    var quantity = 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        val weightFun: (Double) -> Unit = {
            newSets.weight = it
        }

        Card(
            modifier = Modifier.weight(1f),
            elevation = CardDefaults.cardElevation(0.dp),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column() {
                Text(text = stringResource(id = R.string.label_weight_set))
                NumberPickerWithStep(0.0, 200, 1.25, set.weight, weightFun)
            }
        }

        val repFun: (Int) -> Unit = {
            newSets.reps = it
        }

        Card(
            modifier = Modifier.weight(1f),
            elevation = CardDefaults.cardElevation(0.dp),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column() {
                Text(text = stringResource(id = R.string.label_reps_sets))
                NumberPicker(0, 100, set.reps, repFun)
            }
        }

        val quantityFun: (Int) -> Unit = {
            quantity = it
        }

            Card(
                modifier = Modifier.weight(1f),
                elevation = CardDefaults.cardElevation(0.dp),
                shape = RoundedCornerShape(0.dp)
            ) {
                Column() {
                    Text(text = stringResource(id = R.string.label_sets))
                    NumberPicker(0, 200, 0, quantityFun)
                }
            }

    }

    BackHandler {
        //setVM.saveItem(newSets)

        navHostController.navigateUp()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MySetsDetailScreen() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    MySetsDetailScreen(
        id = 1,
        navHostController = NavHostController(LocalContext.current),
        paddingValues = PaddingValues(20.dp),
        appBarTitle = m
    )
}
*/
