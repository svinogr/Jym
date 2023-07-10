package info.upump.mycompose.ui.screens.navigation.botomnavigation

import info.upump.mycompose.R

const val MY_START_CYCLE_ROUTE = "myStartCycleRoute"
const val DEFAULT_START_CYCLE_ROUTE = "defaultStartCycleRoute"
const val PROFILE_START_CYCLE_ROUTE = "ProfileStartCycleRoute"

const val MY_WORKOUT_ROUTE = "myWorkoutRoute"
const val MY_EXERCISE_ROUTE = "myExerciseRoute"
const val MY_SET_ROUTE = "mySetRoute"

const val DEFAULT_WORKOUT_ROUTE = "defaultWorkoutRoute"
const val DEFAULT_EXERCISE_ROUTE = "deafultExerciseRoute"
const val DEFAULT_SET_ROUTE = "defaultSetRoute"

const val PROFILE_SETTING_ROUTE = "myWorkoutRoute"

sealed class NavigationItem(val title: Int, val rout: String, val iconId: Int = -1) {

    object MyCycleNavigationItem : NavigationItem(
        R.string.title_of_mystartcycle_screen,
        MY_START_CYCLE_ROUTE,
        R.drawable.my_cycle
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

    object ProfileNavigationItem :
        NavigationItem(
            R.string.title_of_profile_screen,
            PROFILE_START_CYCLE_ROUTE,
            R.drawable.profile
        )
}