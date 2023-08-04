package info.upump.mycompose.ui.screens.myworkouts

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
import info.upump.mycompose.ui.theme.MyTextLabel12
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

fun getImage(cycle: Cycle, context: Context): Int {

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
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        cycleVM.getCycle(id)
    }

    val modifier = Modifier
        .fillMaxWidth()
        .background(Color.Transparent)
    val modifierValue = Modifier.padding(start = 10.dp, top = 4.dp, end = 8.dp)
    val modifierLabel = Modifier.padding(start = 8.dp)
    val modifierCard = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp, end = 4.dp, top = 4.dp)

    Column(
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .background(color = colorResource(id = R.color.colorBackgroundConstrateLayout)),
    ) {
        Image(
            modifier = modifier
                .height(200.dp)
                .clickable {
                    chageImage()
                },
            painter = painterResource(id = getImage(cycle, context)),
            contentDescription = "image",
        )
        Card(
            modifier = modifierCard,
            elevation = CardDefaults.cardElevation(1.dp),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(
                containerColor =
                colorResource(id = R.color.colorBackgroundCardView)
            )
        ) {

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

                 val dateState = remember{
                     mutableStateOf(cycle.startStringFormatDate)
                 }

                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val text = createRef()
                    val gui = createGuidelineFromStart(0.5f)
                    Text(
                        modifier = modifierValue.clickable {
                            datePickerDialog(cycle, dateState, context).show()
                        },
                        text = dateState.value
                    )
                    Text(modifier = modifierValue.constrainAs(text) {
                        start.linkTo(gui)
                    }, text = cycle.finishStringFormatDate)
                }
            }
        }

        Card(
            modifier = modifierCard,
            elevation = CardDefaults.cardElevation(1.dp),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(
                containerColor =
                colorResource(id = R.color.colorBackgroundCardView)
            )
        ) {
            var text by rememberSaveable {
                mutableStateOf(cycle.comment)
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = modifierLabel,
                    style = MyTextLabel12,
                    text = stringResource(id = R.string.label_description_cycle)
                )

                TextField(modifier = modifierValue.fillMaxWidth(), colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorResource(R.color.colorBackgroundCardView)
                ), value = text!!, onValueChange = {
                    text = it
                })
            }
        }
    }
    BackHandler {
        Log.d("date", "save ${cycle}")
        //     setVM.saveItem(set)

        navHostController.navigateUp()
    }
}



fun datePickerDialog(cycle: Cycle, dateSatte: MutableState<String>, context: Context): DatePickerDialog {
    val c = Calendar.getInstance()
    c.time = cycle.startDate
    val y = c.get(Calendar.YEAR)
    val m = c.get(Calendar.MONTH)
    val d = c.get(Calendar.DAY_OF_MONTH)

    val di = DatePickerDialog(context,
        { _: DatePicker, mY: Int, mM: Int, mD: Int ->
            c.set(mY, mM, mD)
            cycle.startDate = c.time
            dateSatte.value = cycle.startStringFormatDate
            Log.d("date", "${cycle.startDate}")
        }, y, m, d
    )

    return di
}

fun chageImage() {
    TODO("Not yet implemented")
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

