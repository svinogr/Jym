package info.upump.mycompose.ui.screens.screenscomponents

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.theme.MyTextLabel12
import java.util.Date

@Preview(showBackground = true)
@Composable
fun DateCardWithDatePickerPreview() {
    val cycle = Cycle()
    DateCardWithDatePicker(cycle)
}

@Composable
fun DateCardWithDatePicker(
    cycle: Cycle,
    modifierCard: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp, end = 4.dp, top = 4.dp),
    modifierLabel: Modifier = Modifier.padding(start = 8.dp),
    modifierValue: Modifier = Modifier,
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

            val dateStateStart = remember {
                mutableStateOf(cycle.startStringFormatDate)
            }

            val dateStateFinish = remember {
                mutableStateOf(cycle.startStringFormatDate)
            }

            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val text = createRef()
                val gui = createGuidelineFromStart(0.5f)
                Text(
                    modifier = modifierValue.clickable {
                        datePickerDialog(cycle.startDate!!, context) { date ->
                            cycle.startDate = date
                            dateStateStart.value = cycle.startStringFormatDate
                        }.show()
                    },
                    text = dateStateStart.value
                )
                Text(modifier = modifierValue
                    .constrainAs(text) {
                        start.linkTo(gui)
                    }
                    .clickable {
                        datePickerDialog(cycle.finishDate!!, context) { date ->
                            cycle.finishDate = date
                            dateStateFinish.value = cycle.finishStringFormatDate
                        }.show()
                    }, text = dateStateFinish.value
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