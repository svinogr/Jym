package info.upump.mycompose.ui.screens.myworkouts

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
import info.upump.mycompose.ui.theme.MyTextLabel12
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

fun getImage(context: Context): Int {

    val name = context.packageName
    val id = context.resources.getIdentifier("drew", "drawable", name)
    return id
}

@Composable
fun CreateEditeCycleScreen(
    id: Long, navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val cycleVM: CycleVM = viewModel()
    val cycle by cycleVM.cycle.collectAsState()
    val isLoad by cycleVM.isLoading.collectAsState()

    val modifier = Modifier
        .fillMaxWidth()
        .background(Color.Transparent)
    val modifierValue = Modifier.padding(start = 10.dp, top = 4.dp)
    val modifierLabel = Modifier.padding(start = 8.dp)
    val modifierCard = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp, end = 4.dp, top = 4.dp)

    Column(
        modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .background(color = colorResource(id = R.color.colorBackgroundConstrateLayout)),
    ) {
        Image(
            modifier = modifier.height(200.dp),
            painter = painterResource(id = getImage(LocalContext.current)),
            contentDescription = "image"
        )
        Card(modifier = modifierCard,
            elevation = CardDefaults.cardElevation(1.dp),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor =
            colorResource(id = R.color.colorBackgroundCardView))) {

            Column(modifier = Modifier.fillMaxWidth()) {
                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val text = createRef()
                    val gui = createGuidelineFromStart(0.5f)
                    Text(
                        modifier = modifierLabel,
                        style = MyTextLabel12,
                        text = stringResource(id = R.string.label_start_cycle)
                    )
                    Text(
                        modifier = modifierLabel.constrainAs(text)
                        {
                            start.linkTo(gui)
                        },
                        style = MyTextLabel12,
                        text = stringResource(id = R.string.label_finish_cycle)
                    )
                }

                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val text = createRef()
                    val gui = createGuidelineFromStart(0.5f)
                    Text(
                        modifier = modifierValue,
                        text = cycle.startStringFormatDate
                    )
                    Text(modifier = modifierValue.constrainAs(text) {
                        start.linkTo(gui)
                    }, text = cycle.finishStringFormatDate)
                }
            }
        }

        Card(modifier =modifierCard,
            elevation = CardDefaults.cardElevation(1.dp),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor =
             colorResource(id = R.color.colorBackgroundCardView))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = modifierLabel,
                    style = MyTextLabel12,
                    text = stringResource(id = R.string.label_description_cycle)
                )
                Text(
                    modifier = modifierValue,
                    text = "${cycle.comment}"
                )
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCreateEditeCycleScreen() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    CreateEditeCycleScreen(
        id = 1L,
        navHostController = NavHostController(LocalContext.current),
        PaddingValues(20.dp),
        m
    )
}