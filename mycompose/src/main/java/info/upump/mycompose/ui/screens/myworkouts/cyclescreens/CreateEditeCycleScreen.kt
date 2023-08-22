package info.upump.mycompose.ui.screens.myworkouts.cyclescreens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleVMCreateEdit
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DateCardWithDatePicker
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DescriptionCardWithVM
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.ImageTitleImageTitle
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.ImageWithPicker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditeCycleScreen(
    id: Long, navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val visibleFab by remember {
        mutableStateOf(true) // не забыть псотавить фолс для начального
    }
    val snackbarState = remember {
        mutableStateOf(false)
    }
    val density = LocalDensity.current
    val cycleVMCreateEdit: CycleVMCreateEdit = viewModel()
    val isLoad by cycleVMCreateEdit.isLoading.collectAsState()

    val c = cycleVMCreateEdit.item.collectAsState()
    val context = LocalContext.current
    Log.d("CreateEditeCycleScreen", "$id")


    if (id == 0L) {
        appBarTitle.value = context.resources.getString(R.string.cycle_dialog_create_new)
    } else {
        Log.d("CreateEditeCycleScreen appbar set", "${c.value.title}")
        val o = cycleVMCreateEdit.title.collectAsState()
        Log.d("CreateEditeCycleScreen appbar set", "${o.value}")

        appBarTitle.value = o.value
    }

    Scaffold(modifier = Modifier,
        floatingActionButton = {

            AnimatedVisibility(
                visible = visibleFab,
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { 100.dp.roundToPx() }
                },
                exit = slideOutVertically {
                    with(density) { 100.dp.roundToPx() }
                }
            ) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        if (!cycleVMCreateEdit.isBlankFields()) {
                            cycleVMCreateEdit.save() {
                                navHostController.popBackStack()
                                navHostController.navigate(
                                    NavigationItem.DetailCycleNavigationItem.routeWithId(it)
                                )
                            }
                        } else {
                            snackbarState.value = true
                        }
                    },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fab_next),
                            contentDescription = "next"
                        )
                    }
                )
            }
        },
        snackbarHost = {
            val text = "тест"
            if(snackbarState.value) {
                Snackbar(
                    action = {
                        Button(onClick = {
                            snackbarState.value = false
                        }) {
                        }
                    },
                ) { Text(text = text) }
            }
        },
        ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding()
                .verticalScroll(rememberScrollState())
                .background(color = colorResource(id = R.color.colorBackgroundConstrateLayout)),
        ) {
            // of thee parts
            ImageTitleImageTitle(cycleVMCreateEdit) { ImageWithPicker(cycleVMCreateEdit) }
            DateCardWithDatePicker(cycleVMCreateEdit)
            // description aka comment
            DescriptionCardWithVM(cycleVMCreateEdit)
        }
    }

    LaunchedEffect(key1 = true) {
        cycleVMCreateEdit.getBy(id)
    }

    BackHandler {
        navHostController.navigateUp()
    }
}


/*@RequiresApi(Build.VERSION_CODES.P)*/
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



