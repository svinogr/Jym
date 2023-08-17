package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVMInterface
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.VMInterface
import info.upump.mycompose.ui.theme.MyTextLabel12
import info.upump.mycompose.ui.theme.MyTextTitleLabelWithColor
import java.util.Date

@Preview(showBackground = true)
@Composable
fun DateCardWithDatePickerPreview() {
    val cycle = CycleVM.vmOnlyForPreview
    DateCardWithDatePicker(cycle)
}

@Composable
fun DateCardWithDatePicker(
    cycleVM: VMInterface<Cycle>,
    modifierCard: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp, end = 4.dp, top = 4.dp),
    modifierValue: Modifier = Modifier
        .padding(start = 10.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
    context: Context = LocalContext.current
) {
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
            val cycle by cycleVM.item.collectAsState()

            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val text = createRef()
                val gui = createGuidelineFromStart(0.5f)
                TextField(modifier = modifierValue
                ,
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = colorResource(R.color.colorBackgroundCardView)
                    ),
                    readOnly = true,
                    value = cycle.startStringFormatDate,
                    onValueChange = {
                     //   entity.comment = it
                       // comment = it
                    },
                    label = {
                        Text(modifier = Modifier.clickable {
                            datePickerDialog(cycle.startDate!!, context) { date ->
                              cycleVM.updateStartDate(date)
                            //cycle.value.startDate = date
                               // dateStateStart.value = cycle.startStringFormatDate
                            }.show()
                        },
                            text = stringResource(id =  R.string.label_start_cycle),
                            style = MyTextTitleLabelWithColor
                        )
                    }
                )

                TextField(modifier = modifierValue
                    .constrainAs(text)
                    {
                        start.linkTo(gui)
                    }
                    .clickable {

                    },
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = colorResource(R.color.colorBackgroundCardView)
                    ),

                    readOnly = true,
                    value = cycle.finishStringFormatDate,
                    onValueChange = {
                        //entity.comment = it
                        //comment = it
                    },
                    label = {
                        Text(modifier = Modifier.clickable {
                            datePickerDialog(cycle.finishDate, context) { date ->
                                cycleVM.updateFinishDate(date)
                               // cycle.finishDate = date
                               // dateStateFinish.value = cycle.finishStringFormatDate
                            }.show()
                        },
                            text = stringResource(id =  R.string.label_finish_cycle),
                            style = MyTextTitleLabelWithColor,
                        )
                    }
                )
            }
        }
    }
}

fun datePickerDialog(date: Date, context: Context, updateText: (Date) -> Unit): DatePickerDialog {
    val c = Calendar.getInstance()
    c.time = date
    val y = c.get(Calendar.YEAR)
    val m = c.get(Calendar.MONTH)
    val d = c.get(Calendar.DAY_OF_MONTH)

    val di = DatePickerDialog(
        context,
        { _: DatePicker, mY: Int, mM: Int, mD: Int ->
            c.set(mY, mM, mD)
            updateText(c.time)
        }, y, m, d
    )

    return di
}