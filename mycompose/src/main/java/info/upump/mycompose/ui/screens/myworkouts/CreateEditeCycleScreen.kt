package info.upump.mycompose.ui.screens.myworkouts

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
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
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }

    LaunchedEffect(key1 = true) {
        cycleVM.getCycle(id)
    }

    val modifier = Modifier
        .fillMaxWidth()
        .background(Color.Transparent)
    val modifierValue = Modifier.padding(start = 10.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
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
        var title by remember {
            mutableStateOf(cycle.title)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia()
            ){
                cycle.image = it.toString()
                Log.d("IRU", "$it")
            }
            Image(
                modifier = modifier
                    .height(200.dp)
                    .clickable {
                        chageImage(launcher)
                    },
              //  painter = painterResource(id = getImage(cycle, context)),
                bitmap =   getImage2(cycle, context, bitmap).asImageBitmap(),
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 16.dp, start = 10.dp),
                style = MyTextTitleLabel20StrokeText,
                text = if (title!!.isBlank()) {
                    stringResource(id = R.string.title_cycle_hint)
                } else {
                    title!!
                }
            )
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

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = modifierLabel,
                    style = MyTextLabel12,
                    text = stringResource(id = R.string.label_title_cycle)
                )

                TextField(modifier = modifierValue.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = colorResource(R.color.colorBackgroundCardView)
                    ),
                    value = title!!,
                    onValueChange = {
                        cycle.title = title
                        title = it
                    }, placeholder = {
                        Text(stringResource(id = R.string.title_cycle_hint))
                    }
                )
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

        Card(
            modifier = modifierCard,
            elevation = CardDefaults.cardElevation(1.dp),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(
                containerColor =
                colorResource(id = R.color.colorBackgroundCardView)
            )
        ) {
            var comment by rememberSaveable {
                mutableStateOf(cycle.comment)
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = modifierLabel,
                    style = MyTextLabel12,
                    text = stringResource(id = R.string.label_description_cycle)
                )

                TextField(modifier = modifierValue.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = colorResource(R.color.colorBackgroundCardView)
                    ),
                    value = comment!!,
                    onValueChange = {
                        cycle.comment = it
                        comment = it
                    })
            }
        }
    }
    BackHandler {
        Log.d("date", "save ${cycle}")
        cycleVM.saveCycle()

        navHostController.navigateUp()
    }
}

@RequiresApi(Build.VERSION_CODES.P)
fun getImage2(cycle: Cycle, context: Context, bitmap: MutableState<Bitmap?>): Bitmap {
    val name = context.packageName
    var source: ImageDecoder.Source
    Log.d("getImage", "${cycle.defaultImg}")
    Log.d("getImage", "${cycle.image}")
    try {
        if (cycle.image != null) {
            source = ImageDecoder.createSource(context.contentResolver, Uri.parse(cycle.image))

        } else {
            val id = context.resources.getIdentifier(cycle.defaultImg, "drawable", name)
            source = ImageDecoder.createSource(context.resources, id)

        }
    } catch (e: NullPointerException) {
        val id = context.resources.getIdentifier("drew", "drawable", name)
        source = ImageDecoder.createSource(context.resources, id)

    }

    return ImageDecoder.decodeBitmap(source)
}


fun getImage(cycle: Cycle, context: Context): Int {

    val name = context.packageName
    val id = context.resources.getIdentifier("drew", "drawable", name)
    return id
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

fun chageImage(launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>) {
    launcher.launch(PickVisualMediaRequest(
        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
    ))
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

