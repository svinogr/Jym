package info.upump.jym.ui.screens.screenscomponents

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import info.upump.jym.R

@Composable
@SuppressLint("InflateParams")
fun NumberPicker(min: Int, max: Int, initialState: Int, value: (Int) -> Unit) {
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
            picker.setOnValueChangedListener { p0, p1, p2 -> value(p2) }

            return@AndroidView picker
        },
        update = {
            val i = it.displayedValues.indexOf(initialState.toString())
            it.value = i
           // value(it.value)
        }
    )
}
