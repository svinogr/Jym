package info.upump.mycompose.ui.screens.navigation.botomnavigation

import androidx.compose.runtime.Composable
import info.upump.mycompose.R

const val MY_START_CYCLE_ROUTE = "myStartCycleRoute"
const val DEFAULT_START_CYCLE_ROUTE = "defaultStartCycleRoute"
const val PROFILE_START_CYCLE_ROUTE = "ProfileStartCycleRoute"

const val MY_WORKOUT_ROUTE = "myWorkoutRoute"
const val MY_EXERCISE_ROUTE = "myExerciseRoute"
const val MY_SET_ROUTE = "mySetRoute"

const val DEFAULT_CYCLE_DETAIL_ROUTE = "defaultCycleDetail"
const val DETAIL_ARGUMENT_KEY_ID = "{id}"
const val DEFAULT_EXERCISE_ROUTE = "defaultExerciseRoute"
const val DEFAULT_SET_ROUTE = "defaultSetRoute"

const val PROFILE_SETTING_ROUTE = "myWorkoutRoute"


/*sealed class NavigationDetailItem(v, val route: String, val iconId: Int = -1, val ){

}*/

sealed class NavigationItem(val title: Int = -1, val route: String, val iconId: Int = -1) {

    object MyCycleNavigationItem : NavigationItem(
        R.string.title_of_mystartcycle_screen,
        MY_START_CYCLE_ROUTE,
        R.drawable.my_cycle,
    )

    object MyWorkoutNavigationItem : NavigationItem(
        R.string.title_of_mycycle_screen,
        MY_WORKOUT_ROUTE
    )

    object MyExerciseNavigationItem : NavigationItem(
        R.string.title_of_mycycle_screen,
        MY_EXERCISE_ROUTE
    )

    object MySetsNavigationItem : NavigationItem(
        R.string.title_of_mycycle_screen,
        MY_SET_ROUTE
    )

    object DefaultCycleNavigationItem : NavigationItem(
        R.string.title_of_defaultcycle_screen,
        DEFAULT_START_CYCLE_ROUTE,
        R.drawable.default_cycle
    )

    object DefaultDetailCycleNavigationItem : NavigationItem(
        route = "$DEFAULT_CYCLE_DETAIL_ROUTE/$DETAIL_ARGUMENT_KEY_ID"
    ) {
        fun routeWithId(id: Long): String {
            return  this.route.replace(oldValue = DETAIL_ARGUMENT_KEY_ID, newValue = id.toString())
        }
    }

    object ProfileNavigationItem :
        NavigationItem(
            R.string.title_of_profile_screen,
            PROFILE_START_CYCLE_ROUTE,
            R.drawable.profile
        )
}