package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Entity
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVMInterface
import info.upump.mycompose.ui.theme.MyTextTitleLabel20StrokeText
import info.upump.mycompose.ui.theme.MyTextTitleLabelWithColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

@Composable
fun LabelTitleForImage(
    cycleVM: CycleVMInterface, alignmentText: Alignment = Alignment.BottomStart
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val modifier1 = Modifier
            .padding(bottom = 16.dp, start = 10.dp)
            .align(alignmentText)
       val cycle  by cycleVM.cycle.collectAsState()

        TextField(modifier = modifier1,
            textStyle = MyTextTitleLabel20StrokeText,
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            ),
            readOnly = true,
            value = cycle.title,
            onValueChange = {
           // nothing to change
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 1)
@Composable
fun LabelTitleForImagePreview() {
    val cycle = CycleVM.vmOnlyForPreview
    LabelTitleForImage(cycle)
}

