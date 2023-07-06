package info.upump.mycompose.ui.screens.myworkouts

import android.content.ClipData.Item
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.screenscomponents.CycleItemCard
import java.util.Date

@Composable
fun MyCycleScreen(navHostController: NavHostController) {


   // Text(text = cycle.title!!)
   LazyColumn {
       item {
           for (i in 1..20) {
               val cycle = Cycle()
               cycle.title = "pipec $i"
               cycle.image = "uk1"
               cycle.startDate = Date()
               cycle.finishDate = Date()
               CycleItemCard(cycle = cycle)
           }
       }
   }

}