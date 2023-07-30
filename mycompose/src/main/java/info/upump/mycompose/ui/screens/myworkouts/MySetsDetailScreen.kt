package info.upump.mycompose.ui.screens.myworkouts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.SetsVM
import info.upump.mycompose.ui.screens.screenscomponents.NumberPicker
import info.upump.mycompose.ui.screens.screenscomponents.NumberPickerWithStep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MySetsDetailScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val setVM: SetsVM = viewModel()
    val set by setVM.set.collectAsState()
    val isLoad = setVM.isLoading.collectAsState()

    LaunchedEffect(key1 = true){
        setVM.getSetsBy(id)
    }
    
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = paddingValues.calculateTopPadding())) {
        Card(
            modifier = Modifier.weight(1f),
            elevation = CardDefaults.cardElevation(0.dp),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column() {
                Text(text = stringResource(id = R.string.label_weight_set))
                NumberPickerWithStep(0.0, 200, 1.25, set)
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            elevation = CardDefaults.cardElevation(0.dp),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column() {
                Text(text = stringResource(id = R.string.label_reps_sets))
                NumberPicker(0, 100, set.reps)
            }
        }

        if (set.id == 0L) {
            Card(
                modifier = Modifier.weight(1f),
                elevation = CardDefaults.cardElevation(0.dp),
                shape = RoundedCornerShape(0.dp)
            ) {
                Column() {
                    Text(text = stringResource(id = R.string.label_sets))
                    NumberPicker(0, 200, 0)
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewMySetsDetailScreen() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    MySetsDetailScreen(
        id = 1,
        navHostController = NavHostController(LocalContext.current),
        paddingValues = PaddingValues(20.dp),
        appBarTitle = m
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewMySetsDetailEditScreen() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    MySetsDetailScreen(
        id = 0,
        navHostController = NavHostController(LocalContext.current),
        paddingValues = PaddingValues(20.dp),
        appBarTitle = m
    )
}