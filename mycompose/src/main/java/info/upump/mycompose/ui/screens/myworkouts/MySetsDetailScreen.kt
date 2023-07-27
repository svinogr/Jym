package info.upump.mycompose.ui.screens.myworkouts

import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.screenscomponents.NumberPicker
import info.upump.mycompose.ui.screens.screenscomponents.getArrayWithSteps
import info.upump.mycompose.ui.theme.MyTextTitleLabel16
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun MySetsDetailScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = paddingValues.calculateTopPadding())) {
        Card(
            elevation = CardDefaults.cardElevation(0.dp),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column() {
                Text(text = stringResource(id = R.string.label_weight_set))
                NumberPicker()
            }
        }

        Card() {
            Column() {
                Text(text = stringResource(id = R.string.label_reps_sets))
                NumberPicker()
            }
        }

        Card() {
            Column() {
                Text(text = stringResource(id = R.string.label_sets))
                NumberPicker()
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