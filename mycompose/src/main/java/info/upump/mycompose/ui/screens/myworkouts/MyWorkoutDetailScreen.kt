package info.upump.mycompose.ui.screens.myworkouts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MyWorkoutDetailScreen(id: Long, navHostController: NavHostController) {
    Text(text = "MyWorkoutsScreen $id")
}