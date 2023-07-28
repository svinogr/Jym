package info.upump.mycompose.ui.screens.screenscomponents

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Sets

@Composable
@SuppressLint("InflateParams")
fun NumberPicker(min: Int, max: Int, initialState: Int) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.picker, null)
            val picker = view.findViewById<NumberPicker>(R.id.numberPicker)
            picker.minValue = min
            picker.maxValue = max
            picker.descendantFocusability = NumberPicker.FOCUS_BEFORE_DESCENDANTS

            val valuesForDisplay = Array<String>(size = max + 1) {
                (min + (it )).toString()
            }
            picker.displayedValues = valuesForDisplay
            return@AndroidView picker
        },
        update = {
            val i = it.displayedValues.indexOf(initialState.toString())
            it.value = i
        }
    )
}
