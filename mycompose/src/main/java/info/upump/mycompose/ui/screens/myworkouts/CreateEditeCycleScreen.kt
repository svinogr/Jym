package info.upump.mycompose.ui.screens.myworkouts

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Entity
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
import info.upump.mycompose.ui.screens.screenscomponents.CardTitle
import info.upump.mycompose.ui.screens.screenscomponents.DateCardWithDatePicker
import info.upump.mycompose.ui.screens.screenscomponents.DescriptionCard
import info.upump.mycompose.ui.screens.screenscomponents.ImageWithPicker
import info.upump.mycompose.ui.screens.screenscomponents.LabelTitleForImage
import info.upump.mycompose.ui.theme.MyTextLabel12
import info.upump.mycompose.ui.theme.MyTextTitleLabel20StrokeText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date


@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalTextApi::class)
@Composable
fun CreateEditeCycleScreen(
    id: Long, navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val cycleVM: CycleVM = viewModel()
    val cycle by cycleVM.cycle.collectAsState()
    val isLoad by cycleVM.isLoading.collectAsState()

    LaunchedEffect(key1 = true) {
        cycleVM.getCycle(id)
    }

    Column(
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .background(color = colorResource(id = R.color.colorBackgroundConstrateLayout)),
    ) {
        var title by remember {
            mutableStateOf(cycle.title!!)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            // Image
            ImageWithPicker(cycle)
            // Label for image
            val labelTitleBoxModifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 16.dp, start = 10.dp)
            LabelTitleForImage(cycle, modifier = labelTitleBoxModifier)
        }

        CardTitle(cycle)

        DateCardWithDatePicker(cycle)

        // description aka comment
        DescriptionCard(cycle)
    }
    BackHandler {
        Log.d("date", "save ${cycle}")
        cycleVM.saveCycle()

        navHostController.navigateUp()
    }
}




@RequiresApi(Build.VERSION_CODES.P)
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


@Composable
fun ImageTitleImageTitle(cycle: Cycle) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            ImageWithPicker(cycle)
            val labelTitleBoxModifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 16.dp, start = 10.dp)
            LabelTitleForImage(entity = cycle, labelTitleBoxModifier)
        }

        CardTitle(cycle = cycle)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ImageTitleImageTitlePreview() {
    val cycle = Cycle().apply {
        title = "Новая программа"
    }
    ImageTitleImageTitle(cycle)
}



