package info.upump.mycompose.ui.screens.myworkouts.cyclescreens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.myworkouts.ActionState
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleVMCreateEdit
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.FloatActionButtonWithState
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
    appBarTitle: MutableState<String>,
    action: ActionState
) {
    val cycleVMCreateEdit: CycleVMCreateEdit = viewModel()
    val isLoad by cycleVMCreateEdit.isLoading.collectAsState()

  //  val c = cycleVMCreateEdit.item.collectAsState()
    val context = LocalContext.current
    Log.d("CreateEditeCycleScreen", "$id")
    val columnModifier = Modifier
        .fillMaxHeight()
        .padding()
        .verticalScroll(rememberScrollState())
        .background(color = colorResource(id = R.color.colorBackgroundConstrateLayout))

    if (action == ActionState.CREATE) {
        appBarTitle.value = context.resources.getString(R.string.cycle_dialog_create_new)
    }
    if (action == ActionState.UPDATE){
      //  Log.d("CreateEditeCycleScreen appbar set", "${c.value.title}")
    //    val o = cycleVMCreateEdit.title.collectAsState()
      //  Log.d("CreateEditeCycleScreen appbar set", "${o.value}")

        appBarTitle.value = "Редактирование"
    }

    Scaffold(modifier = Modifier,
        floatingActionButton = {
                FloatActionButtonWithState(isVisible = true, icon =R.drawable.ic_fab_next) {
                    if (!cycleVMCreateEdit.isBlankFields()) {
                        cycleVMCreateEdit.save() {
                            if (action == ActionState.CREATE) {
                                navHostController.popBackStack()
                                navHostController.navigate(
                                    NavigationItem.DetailCycleNavigationItem.routeWithId(it)
                                )
                            } else {
                                navHostController.navigateUp()
                            }
                        }
                    }
                }
        }
    ) {
        Column(modifier = columnModifier)
        {
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
        id = 0L,
        navHostController = NavHostController(LocalContext.current),
        PaddingValues(20.dp),
        m,
        ActionState.CREATE
    )
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCreateEditeCycleScreen2() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    CreateEditeCycleScreen(
        id = 1L,
        navHostController = NavHostController(LocalContext.current),
        PaddingValues(20.dp),
        m,
        ActionState.UPDATE
    )
}





