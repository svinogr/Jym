package info.upump.mycompose.ui.screens.navigation.botomnavigation

import info.upump.mycompose.R

const val MY_START_CYCLE_ROUTE = "myStartCycleRoute"
const val DEFAULT_START_CYCLE_ROUTE = "defaultStartCycleRoute"
const val PROFILE_START_CYCLE_ROUTE = "ProfileStartCycleRoute"

const val MY_WORKOUT_ROUTE = "myWorkoutRoute"
const val MY_EXERCISE_ROUTE = "myExerciseRoute"
const val MY_SET_ROUTE = "mySetRoute"

const val DEFAULT_CYCLE_DETAIL_ROUTE = "defaultCycleDetail"
const val CYCLE_DETAIL_ROUTE = "cycleDetail"

const val DEFAULT_WORKOUT_DETAIL_ROUTE = "defaultWorkoutDetail"
const val WORKOUT_DETAIL_ROUTE = "workoutDetail"
const val EXERCISE_DETAIL_ROUTE = "exerciseDetail"
const val SETS_DETAIL_ROUTE = "setsDetail"
const val DETAIL_ARGUMENT_KEY_ID = "{id}"
const val DETAIL_ARGUMENT_KEY_PARENT_ID = "{parentId}"
const val CYCLE_EDITE_ROUTE = "cycleEditCreate"
const val WORKOUT_CREATE_ROUTE = "workoutCreate"
const val WORKOUT_EDITE_ROUTE = "workoutEditCreate"

const val DEFAULT_EXERCISE_ROUTE = "defaultExerciseRoute"
const val DEFAULT_SET_ROUTE = "defaultSetRoute"

const val PROFILE_SETTING_ROUTE = "myWorkoutRoute"


sealed class NavigationItem(val title: Int = -1, val route: String, val iconId: Int = -1) {
    object DetailWorkoutNavigationItem : NavigationItem(
        route = "$WORKOUT_DETAIL_ROUTE/$DETAIL_ARGUMENT_KEY_ID"
    ) {
        fun routeWithId(id: Long): String {
            return  this.route.replace(oldValue = DETAIL_ARGUMENT_KEY_ID, newValue = id.toString())
        }
    }

    object MyCycleNavigationItem : NavigationItem(
        R.string.title_of_mystartcycle_screen,
        MY_START_CYCLE_ROUTE,
        R.drawable.my_cycle,
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
   object DetailCycleNavigationItem : NavigationItem(
        route = "$CYCLE_DETAIL_ROUTE/$DETAIL_ARGUMENT_KEY_ID"
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

    object DetailSetDetailNavigationItem : NavigationItem(
        route = "$SETS_DETAIL_ROUTE/$DETAIL_ARGUMENT_KEY_ID"
    ){
        fun routeWithId(id: Long): String {
            return  this.route.replace(oldValue = DETAIL_ARGUMENT_KEY_ID, newValue = id.toString())
        }
    }

    object DetailExerciseNavigationItem : NavigationItem(
        route = "$EXERCISE_DETAIL_ROUTE/$DETAIL_ARGUMENT_KEY_ID"
    )
    {
        fun routeWithId(id: Long): String {
            return  this.route.replace(oldValue = DETAIL_ARGUMENT_KEY_ID, newValue = id.toString())
        }
    }

    object CreateSetsNavigationItem : NavigationItem(
        route = "$SETS_DETAIL_ROUTE/$DETAIL_ARGUMENT_KEY_ID"
    ) {
        fun routeWithId(id: Long): String {
            return  this.route.replace(oldValue = DETAIL_ARGUMENT_KEY_ID, newValue = id.toString())
        }
    }
    // переход на экран редактирования или создания cycle
    object CreateEditeCycleNavigationItem : NavigationItem(
        route = "$CYCLE_EDITE_ROUTE/$DETAIL_ARGUMENT_KEY_ID"
    ) {
        fun routeWith(id: Long): String {
            return  this.route.replace(oldValue = DETAIL_ARGUMENT_KEY_ID, newValue = id.toString())
        }
    }

    // переход на экран создания workout с id родителя
    object CreateWorkoutNavigationItem : NavigationItem(
        route = "$WORKOUT_CREATE_ROUTE/$DETAIL_ARGUMENT_KEY_PARENT_ID"
    ) {
        fun routeWith(parentId: Long): String {
            return  this.route.replace(oldValue = DETAIL_ARGUMENT_KEY_PARENT_ID, newValue = parentId.toString())
        }
    }

/*
    object EditeWorkoutNavigationItem : NavigationItem(
        route = "$WORKOUT_EDITE_ROUTE/$DETAIL_ARGUMENT_KEY_ID"
    ) {
        fun routeWithId(id: Long): String {
            return  this.route.replace(oldValue = DETAIL_ARGUMENT_KEY_ID, newValue = id.toString())
        }
    }
*/



}