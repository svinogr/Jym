package info.upump.mycompose.ui.screens.navigation.botomnavigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jymcompose.ui.screens.ProfileScreen.ProfileScreen
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.defaultscreen.DefaultWorkoutsScreen
import info.upump.mycompose.ui.screens.myworkouts.MyCycleScreen
import info.upump.mycompose.ui.screens.myworkouts.MyExerciseScreen
import info.upump.mycompose.ui.screens.myworkouts.MyStartCycleScreen
import info.upump.mycompose.ui.screens.myworkouts.MyWorkoutsScreen


@Composable
fun NavGraph(navHostController: NavHostController) {
    val context = LocalContext.current
    // val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        startDestination = stringResource(id = R.string.my_cycle_rout)
    ) {

        composable(route = context.getString(R.string.my_workout_rout)) {
            MyWorkoutsScreen()
        }

        composable(route = context.getString(R.string.my_exercise_rout)) {
            MyExerciseScreen()
        }

        composable(route = context.getString(R.string.my_cycle_rout)) {
            MyCycleScreen(navHostController)
        }

        /*      navigation(
                  route = context.getString(R.string.my_startcycle_rout),
                  startDestination = context.getString(R.string.my_cycle_rout)
              ) {
                 navigation(
                     route = context.getString(R.string.my_cycle_rout),
                     startDestination = context.getString(R.string.my_workout_rout)
                 ) {
                     *//* composable(route = context.getString(R.string.my_cycle_rout)) {
                MyCycleScreen(navHostController)
            }*//*

               composable(route = context.getString(R.string.my_workout_rout)) {
                   MyWorkoutsScreen()
               }
           }

            composable(route = context.getString(R.string.my_exercise_rout)) {
                MyExerciseScreen()
            }

        }*/


        composable(route = context.getString(R.string.default_cycle_rout)) {
            DefaultWorkoutsScreen()
        }

        composable(route = context.getString(R.string.profile_rout)) {
            Log.d("context", context.getString(R.string.profile_rout))
            ProfileScreen()
        }
    }

}