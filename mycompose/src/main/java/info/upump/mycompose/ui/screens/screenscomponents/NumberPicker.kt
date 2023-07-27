package info.upump.mycompose.ui.screens.screenscomponents

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import info.upump.mycompose.R

@SuppressLint("InflateParams")
@Composable
fun NumberPicker() {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.picker, null)
            val picker = view.findViewById<NumberPicker>(R.id.numberPicker)
            val min = 0.0
            val max = 200
            val step = 1.25
            val valueFor  = getArrayWithSteps(min, max, step)

            picker.minValue = 0
            picker.maxValue = 200
            picker.displayedValues = valueFor
            picker.descendantFocusability = NumberPicker.FOCUS_BEFORE_DESCENDANTS

           /* datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ) { _, year, monthOfYear, dayOfMonth ->
                val date = Calendar.getInstance().apply {
                    set(year, monthOfYear, dayOfMonth)
                }.time
                onSelectedDateUpdate(date)
            }*/
           return@AndroidView picker
        }
    )
}


fun getArrayWithSteps(iMinValue: Double, iMaxValue: Int, iStep: Double): Array<String> {
    val iStepsArray = 800;

    val arrayValues = Array<String>(size = iStepsArray) {
        (iMinValue + (it * iStep)).toString()
    }

    return arrayValues;
}
