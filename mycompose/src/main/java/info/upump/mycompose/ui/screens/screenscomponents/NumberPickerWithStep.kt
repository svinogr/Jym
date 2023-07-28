package info.upump.mycompose.ui.screens.screenscomponents

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.room.util.EMPTY_STRING_ARRAY
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Sets

@SuppressLint("InflateParams")
@Composable
fun NumberPickerWithStep(min: Double, max: Int, step: Double, initialState: Sets) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.picker, null)
            val picker = view.findViewById<NumberPicker>(R.id.numberPicker)

            val iStepsArray = 800;
            val valuesForDisplay = Array<String>(size = iStepsArray) {
                (min + (it * step)).toString()
            }


            picker.minValue = 0
            picker.maxValue = 200
            picker.displayedValues = valuesForDisplay
            picker.descendantFocusability = NumberPicker.FOCUS_BEFORE_DESCENDANTS

            return@AndroidView picker
        },
        update = {
            val i = it.displayedValues.indexOf(initialState.weight.toString())
            it.value = i
        }
    )
}
